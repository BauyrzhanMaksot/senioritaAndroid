package com.example.user.senioritaandroid.Client.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.Client.Adapter.RequestClientListAdapter;
import com.example.user.senioritaandroid.Client.Request;
import com.example.user.senioritaandroid.R;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MyRequestsActivity extends AppCompatActivity {

    ListView listView;
    Context thisContext;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
        listView = findViewById(R.id.my_requests_list_view);
        thisContext = this;
        retrofit = (new Connection(MyRequestsActivity.this)).getRetrofit();
        getRequests(thisContext);
    }

    public void getRequests(final Context context) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<List<Request>> offers = apiService.getMyRequests();
        offers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Request>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(List<Request> requests) {
                        Log.v("Requests:", requests.toString());
                        RequestClientListAdapter adapter = new RequestClientListAdapter(context, R.layout.adapter_view_my_requests, requests);
                        listView.setAdapter(adapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }
}
