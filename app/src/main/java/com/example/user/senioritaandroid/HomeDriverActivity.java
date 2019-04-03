package com.example.user.senioritaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.user.senioritaandroid.Driver.Activity.AcceptedRequestsActivity;
import com.example.user.senioritaandroid.Driver.Activity.CurrentRequestsActivity;
import com.example.user.senioritaandroid.Driver.Activity.HistoryActivity;
import com.example.user.senioritaandroid.Driver.Activity.MyOffersActivity;
import com.example.user.senioritaandroid.User.ProfileActivity;

public class HomeDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
    }

    public void onCurrentRequest(View view) {
        Intent currentRequest = new Intent(HomeDriverActivity.this, CurrentRequestsActivity.class);
        startActivity(currentRequest);
    }

    public void onAcceptedRequest(View view) {
        Intent acceptedRequest = new Intent(HomeDriverActivity.this, AcceptedRequestsActivity.class);
        startActivity(acceptedRequest);
    }

    public void onHistory(View view) {
        Intent history = new Intent(HomeDriverActivity.this, HistoryActivity.class);
        startActivity(history);
    }

    public void onMyProfile(View view) {
        Intent myProfile = new Intent(HomeDriverActivity.this, ProfileActivity.class);
        startActivity(myProfile);
    }

    public void onMyOffers(View view) {
        Intent myOffers = new Intent(HomeDriverActivity.this, MyOffersActivity.class);
        startActivity(myOffers);
    }
}
