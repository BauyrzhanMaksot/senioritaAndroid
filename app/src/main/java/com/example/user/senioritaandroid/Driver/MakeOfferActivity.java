package com.example.user.senioritaandroid.Driver;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.senioritaandroid.ApiService;
import com.example.user.senioritaandroid.Driver.Offer;
import com.example.user.senioritaandroid.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeOfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);
        final Button makeOffer = (Button) findViewById(R.id.button4);

        makeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeOffer();
            }
        });
    }

    public boolean makeOffer() {
        EditText pointA = (EditText) findViewById(R.id.editText3);
        EditText pointB = (EditText) findViewById(R.id.editText4);
        EditText price  = (EditText) findViewById(R.id.editText5);
        Offer offer = new Offer(pointA.getText().toString(), pointB.getText().toString(), price.getText().toString());

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                if (chain.request().header("noToken") == "true") {
                    return chain.proceed(chain.request());
                }
                SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                String token = preferences.getString("token","");
                Log.v("Token", token);
                Request newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer "+token).build();
                return chain.proceed(newRequest);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Single<String> makeOfferResult = apiService.putOffer(offer);
        makeOfferResult.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(String result) {
                        Log.v("Result:", result);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
        return true;
    }

}
