package com.example.user.senioritaandroid.Client.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.Client.Adapter.OfferClientListAdapter;
import com.example.user.senioritaandroid.Driver.Offer;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.Extra.Refresh;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CurrentOffersActivity extends AppCompatActivity implements Refresh {

    ListView listView;
    Context thisContext;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_offers);
        thisContext = this;
        listView = (ListView) findViewById(R.id.list_view_current_offers);
        retrofit = (new Connection(CurrentOffersActivity.this)).getRetrofit();
        getOffers(thisContext);
    }

    public boolean getOffers(final Context context) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<ArrayList<Offer>> requests = apiService.getOffers();
        requests.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Offer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(ArrayList<Offer> requests) {
                        Log.v("RequestDriver:", requests.toString());
                        OfferClientListAdapter adapter = new OfferClientListAdapter(context, R.layout.adapter_view_offers_client, requests);
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
