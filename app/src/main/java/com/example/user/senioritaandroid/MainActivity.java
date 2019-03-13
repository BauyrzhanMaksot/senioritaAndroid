package com.example.user.senioritaandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.senioritaandroid.Driver.DriverActivity;
import com.example.user.senioritaandroid.User.Token;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button loginB;
    EditText userNameE, passwordE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginB = (Button) findViewById(R.id.button);
        userNameE = (EditText)findViewById(R.id.editText);
        passwordE = (EditText)findViewById(R.id.editText2);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login(userNameE.getText().toString(), passwordE.getText().toString());
            }
        });
    }

    public Boolean login(String userName, String password) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                if (chain.request().header("noToken") == "true") {
                    return chain.proceed(chain.request());
                }
                SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                String token = preferences.getString("token","");
                Request newRequest = chain.request().newBuilder().addHeader("Authorization", token).build();
                return chain.proceed(newRequest);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
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
                        Intent getVerified = new Intent(MainActivity.this, DriverActivity.class);
                        startActivity(getVerified);
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
        return true;
    }

    public static String getAuthorizationHeader() {
        String credential =  "client:secret";
        return "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
    }
}
