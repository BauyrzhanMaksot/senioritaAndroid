package com.example.user.senioritaandroid.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Client.HomeClientActivity;
import com.example.user.senioritaandroid.Driver.HomeDriverActivity;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.Register.RegisterActivity;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.User.Token;
import com.example.user.senioritaandroid.User.User;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    Button loginB, registerB;
    EditText userNameE, passwordE;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = (new Connection(LoginActivity.this)).getRetrofit();
        loginB = (Button) findViewById(R.id.button);
        userNameE = (EditText)findViewById(R.id.editText);
        passwordE = (EditText)findViewById(R.id.editText2);
        registerB = (Button) findViewById(R.id.buttonR);
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login(userNameE.getText().toString(), passwordE.getText().toString());
            }
        });
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    register();
            }
        });
    }

    public boolean register() {
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
        return true;
    }

    public Boolean login(String userName, String password) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<Token> token = apiService.loginAccount("password", userName, password, getAuthorizationHeader(), "application/x-www-form-urlencoded; charset=utf-8",true);
        token.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("DISPOSABLE:", d.toString());
                    }

                    @Override
                    public void onSuccess(Token token) {
                        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                        preferences.edit().putString("token", token.getAccessToken()).commit();
                        Log.v("SUCCESS:", token.toString());
                        redirectDriverClient();
                    }

                    @Override
                    public void onError(Throwable e) {
                        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.login_error)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        builder.show();
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException)e).response().errorBody();
                            Log.e("ERRORLogin", e.toString());
                            Log.e("ERRORLogin", responseBody.toString());
                        } else if (e instanceof SocketTimeoutException) {
                            Log.e("ERRORLogin", "SocketTimeout");
                        } else if (e instanceof IOException) {
                            Log.e("ERRORLogin", "IOE " +   e);
                        } else {
                            Log.e("ERRORLogin", "UNK");
                        }
                    }
                });
        return true;
    }

    public void redirectDriverClient() {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<User> user = apiService.getUser();
        user.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("DISPOSABLE:", d.toString());
                    }

                    @Override
                    public void onSuccess(User user) {
                        Log.v("User:", user.toString());
                        if (user.getRole().getId()==2) {
                            Intent role = new Intent(LoginActivity.this, HomeClientActivity.class);
                            startActivity(role);
                        } else {
                            Intent role = new Intent(LoginActivity.this, HomeDriverActivity.class);
                            startActivity(role);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException)e).response().errorBody();
                            Log.e("ERRORMAIN", e.toString());
                            Log.e("ERRORMAIN", responseBody.toString());
                        } else if (e instanceof SocketTimeoutException) {
                            Log.e("ERRORMAIN", "SocketTimeout");
                        } else if (e instanceof IOException) {
                            Log.e("ERRORMAIN", "IOE " +   e);
                        } else {
                            Log.e("ERRORMAIN", "UNK");
                        }
                    }
                });
    }

    public static String getAuthorizationHeader() {
        String credential =  "client:secret";
        return "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
    }
}
