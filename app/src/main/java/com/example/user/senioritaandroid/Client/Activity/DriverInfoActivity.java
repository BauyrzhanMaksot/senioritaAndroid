package com.example.user.senioritaandroid.Client.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.User.User;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DriverInfoActivity extends AppCompatActivity {

    private User currentUser;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);
        retrofit = (new Connection(DriverInfoActivity.this)).getRetrofit();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        Long userId = extras.getLong("id");
        if (userId != null) {
            getUserInfo(userId);
        }
    }

    public boolean getUserInfo(Long userId) {
            ApiService apiService = retrofit.create(ApiService.class);
            Single<User> user = apiService.getDriver(userId);
            user.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.v("disposable", d.toString());
                        }
                        @Override
                        public void onSuccess(User user) {
                            currentUser = user;
                            Log.v("User:", user.toString());
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", e.toString());
                        }
                    });
        return true;
    }
}
