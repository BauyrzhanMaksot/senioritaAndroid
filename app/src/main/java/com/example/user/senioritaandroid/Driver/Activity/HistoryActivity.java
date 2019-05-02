package com.example.user.senioritaandroid.Driver.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.Driver.Adapter.HistoryDriverAdapter;
import com.example.user.senioritaandroid.Extra.Order;
import com.example.user.senioritaandroid.R;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    Context thisContext;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.driver_history_view_list);
        thisContext = this;
        retrofit = (new Connection(HistoryActivity.this)).getRetrofit();
        getHistory(thisContext);
    }


    public boolean getHistory(final Context context) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<List<Order>> requests = apiService.getHistory();
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
                        HistoryDriverAdapter adapter = new HistoryDriverAdapter(context, R.layout.adapter_view_history_driver, requests);
                        listView.setAdapter(adapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
        return true;
    }
}
