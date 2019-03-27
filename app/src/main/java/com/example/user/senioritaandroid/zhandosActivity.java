package com.example.user.senioritaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.user.senioritaandroid.Client.AcceptedRequestsActivity;
import com.example.user.senioritaandroid.Client.CurrentRequestsActivity;
import com.example.user.senioritaandroid.Client.HistoryActivity;
import com.example.user.senioritaandroid.Client.MyOffersActivity;
import com.example.user.senioritaandroid.User.ProfileActivity;

public class ZhandosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhandos);
    }

    public void onCurrentRequest(View view) {
        Intent currentRequest = new Intent(ZhandosActivity.this, CurrentRequestsActivity.class);
        startActivity(currentRequest);
    }

    public void onAcceptedRequest(View view) {
        Intent acceptedRequest = new Intent(ZhandosActivity.this, AcceptedRequestsActivity.class);
        startActivity(acceptedRequest);
    }

    public void onHistory(View view) {
        Intent history = new Intent(ZhandosActivity.this, HistoryActivity.class);
        startActivity(history);
    }

    public void onMyProfile(View view) {
        Intent myProfile = new Intent(ZhandosActivity.this, ProfileActivity.class);
        startActivity(myProfile);
    }

    public void onMyOffers(View view) {
        Intent myOffers = new Intent(ZhandosActivity.this, MyOffersActivity.class);
        startActivity(myOffers);
    }
}
