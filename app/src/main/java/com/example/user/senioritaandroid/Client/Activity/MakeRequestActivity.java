package com.example.user.senioritaandroid.Client.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.senioritaandroid.ApiService;
import com.example.user.senioritaandroid.Constant;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.User.Street;
import com.example.user.senioritaandroid.User.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class MakeRequestActivity extends AppCompatActivity {

    AutoCompleteTextView pointA, pointB;
    EditText price;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        getStreets();
    }

    public void getStreets() {
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
                .baseUrl(Constant.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Single<List<Street>> streets = apiService.getStreets();
        streets.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Street>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(List<Street> streets) {
                        Log.v("Streets :", streets.toString());
                        setData(streets);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

    public boolean setData(List<Street> streets) {
        pointA = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        pointB= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        price = (EditText)findViewById(R.id.priceMakeRequestClient);
        button = (Button)findViewById(R.id.buttonMakeRequestClient);
        final ArrayList<String> streetsName = new ArrayList<String>(streets.size());
        for (int i=0; i<streets.size(); i++) {
            streetsName.add(streets.get(i).getStreetName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, streetsName);
        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, streetsName);

        pointA.setAdapter(adapter);
        pointA.setThreshold(1);

        pointB.setAdapter(adapter1);
        pointB.setThreshold(1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("MakeRequest", pointA.getText().toString());
                if (pointA.getText().toString().isEmpty() || pointB.getText().toString().isEmpty() || price.getText().toString().isEmpty()) {
                  //TODO show Error
                    Log.v("MakeRequest","Inputs can not be empty");
                } else if (streetsName.indexOf(pointA.getText().toString()) == -1 || streetsName.indexOf(pointB.getText().toString()) == -1) {
                    Log.v("MakeRequest", "You entered wrong value");
                } else {
                    makeRequest();
                }
            }
        });
        return true;
    }

    public boolean makeRequest() {
        String pointA = this.pointA.getText().toString();
        String pointB = this.pointB.getText().toString();
        String price  = this.price.getText().toString();
        com.example.user.senioritaandroid.Client.Request request = new com.example.user.senioritaandroid.Client.Request(pointA, pointB, price);
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
                .baseUrl(Constant.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Single<String> result = apiService.putRequest(request);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(String result) {
                        Log.v("Result :", result);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
        return true;
    }
}
