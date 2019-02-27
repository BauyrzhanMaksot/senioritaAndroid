package com.example.user.senioritaandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    Button loginB;
    EditText userNameE, passwordE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiService apiService = retrofit.create(ApiService.class);
        Single<User> user = apiService.getUser();
        user.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(User user) {
                        Log.v("success", user.toString());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });

        Single<Token> token = apiService.loginAccount("password", "test0", "12345678", getAuthorizationHeader(), "application/x-www-form-urlencoded; charset=utf-8",true);
        token.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Token>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   Log.v("DISPOSABLE:", d.toString());
                               }

                               @Override
                               public void onSuccess(Token token) {
                                   Log.v("SUCCESS:", token.toString());
                               }

                               @Override
                               public void onError(Throwable e) {
                                   if (e instanceof HttpException) {
                                       ResponseBody responseBody = ((HttpException)e).response().errorBody();
                                       Log.e("ERROR", e.toString());
                                       Log.e("ERROR", responseBody.toString());
                                   } else if (e instanceof SocketTimeoutException) {
                                       Log.e("ERROR", "SocketTimeout");
                                   } else if (e instanceof IOException) {
                                       Log.e("ERROR", "IOE");
                                   } else {
                                       Log.e("ERROR", "UNK");
                                   }
                               }
                           });
        loginB = (Button) findViewById(R.id.button);
        userNameE = (EditText)findViewById(R.id.editText);
        passwordE = (EditText)findViewById(R.id.editText2);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameE.getText().toString().equals("admin") &&
                        passwordE.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String getAuthorizationHeader() {
        String credential =  "client:secret";
        return "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
    }
}
