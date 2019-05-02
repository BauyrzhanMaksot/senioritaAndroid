package com.example.user.senioritaandroid.Driver.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.Driver.Adapter.OfferListAdapter;
import com.example.user.senioritaandroid.Driver.Offer;
import com.example.user.senioritaandroid.R;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MyOffersActivity extends AppCompatActivity {

    ListView listView;
    Context thisContext;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoffers);
        listView = findViewById(R.id.driver_my_offers_list_view);
        retrofit = (new Connection(MyOffersActivity.this)).getRetrofit();
        thisContext = this;
        getOffers(thisContext);
    }

    public void getOffers(final Context context) {
        ApiService apiService = retrofit.create(ApiService.class);
        Single<List<Offer>> offers = apiService.getMyOffers();
        offers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Offer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.v("disposable", d.toString());
                    }
                    @Override
                    public void onSuccess(List<Offer> offers) {
                        Log.v("Offers:", offers.toString());
                        OfferListAdapter adapter = new OfferListAdapter(context, R.layout.adapter_view_my_offers_driver, offers);
                        listView.setAdapter(adapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }
}
