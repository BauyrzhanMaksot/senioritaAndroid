package com.example.user.senioritaandroid.Driver.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.Driver.Adapter.RequestAcceptedListAdapter;
import com.example.user.senioritaandroid.Extra.Order;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.Extra.Refresh;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AcceptedRequestsActivity extends AppCompatActivity implements Refresh {

    ListView listView;
    Context thisContext;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);
        listView = (ListView) findViewById(R.id.activity_accepted_list_view);
        thisContext = this;
        retrofit = (new Connection(AcceptedRequestsActivity.this)).getRetrofit();
        getAcceptedRequests(thisContext);
    }

    public boolean getAcceptedRequests(final Context context) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<List<Order>> requests = apiService.getAcceptedRequests();
        requests.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Order>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(List<Order> requests) {
                        Log.v("RequestDriver:", requests.toString());
                        RequestAcceptedListAdapter adapter = new RequestAcceptedListAdapter(context, R.layout.adapter_view_accepted_driver, requests);
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
