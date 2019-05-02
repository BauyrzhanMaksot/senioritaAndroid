package com.example.user.senioritaandroid.Driver.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.Client.Request;
import com.example.user.senioritaandroid.Driver.Adapter.RequestListAdapter;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.Extra.Refresh;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CurrentRequestsActivity extends AppCompatActivity implements Refresh {

    ListView listView;
    Context thisContext;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_requests);
        thisContext = this;
        listView = (ListView) findViewById(R.id.list_view);
        retrofit = (new Connection(CurrentRequestsActivity.this)).getRetrofit();
        getRequests(thisContext);
    }

    public boolean getRequests(final Context context) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<ArrayList<Request>> requests = apiService.getRequests();
        requests.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Request>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(ArrayList<Request> requests) {
                        Log.v("RequestDriver:", requests.toString());
                        RequestListAdapter adapter = new RequestListAdapter(context, R.layout.adapter_view, requests);
                        listView.setAdapter(adapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
        return true;
    }

    @Override
    public void yourDesiredMethod() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
