package com.example.user.senioritaandroid.Driver.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.senioritaandroid.Extra.Connection;
import com.example.user.senioritaandroid.Service.ApiService;
import com.example.user.senioritaandroid.Driver.Offer;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.User.Street;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MakeOfferActivity extends AppCompatActivity {

    AutoCompleteTextView pointA, pointB;
    EditText price;
    Button button;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);
        retrofit = (new Connection(MakeOfferActivity.this)).getRetrofit();
        getStreets();
    }

    public void getStreets() {
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
        pointA = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewDriver);
        pointB= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewDriver2);
        price = (EditText)findViewById(R.id.priceMakeOffer);
        button = (Button)findViewById(R.id.buttonMakeOffer);
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
                Log.v("MakeOffer", pointA.getText().toString());
                if (pointA.getText().toString().isEmpty()) {
                    pointA.setError("Point A can not be empty");
                    Log.v("MakeOffer","Inputs can not be empty");
                } else if (pointB.getText().toString().isEmpty()) {
                    pointB.setError("Point B can not be empty");
                } else if (price.getText().toString().isEmpty()) {
                    price.setError("Price can not be empty");
                } else if (streetsName.indexOf(pointA.getText().toString()) == -1) {
                    pointA.setError("You entered wrong value");
                } else if (streetsName.indexOf(pointB.getText().toString()) == -1) {
                    pointB.setError("You entered wrong value");
                    Log.v("MakeOffer", "You entered wrong value");
                } else {
                    makeOffer();
                }
            }
        });
        return true;
    }

    public boolean makeOffer() {
        String pointA = this.pointA.getText().toString();
        String pointB = this.pointB.getText().toString();
        String price  = this.price.getText().toString();
        Offer offer = new Offer(pointA, pointB, price);

        ApiService apiService = retrofit.create(ApiService.class);
        Single<String> result = apiService.putOffer(offer);
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
