package com.example.user.senioritaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.user.senioritaandroid.Client.Activity.AcceptedOffersActivity;
import com.example.user.senioritaandroid.Client.Activity.CurrentOffersActivity;
import com.example.user.senioritaandroid.Client.Activity.HistoryClientActivity;
import com.example.user.senioritaandroid.Client.Activity.MyRequestsActivity;
import com.example.user.senioritaandroid.Driver.Activity.AcceptedRequestsActivity;
import com.example.user.senioritaandroid.Driver.Activity.CurrentRequestsActivity;
import com.example.user.senioritaandroid.Driver.Activity.HistoryActivity;
import com.example.user.senioritaandroid.Driver.Activity.MyOffersActivity;
import com.example.user.senioritaandroid.User.ProfileActivity;

public class HomeClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
    }

    public void onCurrentOffers(View view) {
        Intent currentRequest = new Intent(HomeClientActivity.this, CurrentOffersActivity.class);
        startActivity(currentRequest);
    }

    public void onAcceptedOffers(View view) {
        Intent acceptedOffers = new Intent(HomeClientActivity.this, AcceptedOffersActivity.class);
        startActivity(acceptedOffers);
    }

    public void onHistory(View view) {
        Intent history = new Intent(HomeClientActivity.this, HistoryClientActivity.class);
        startActivity(history);
    }

    public void onMyProfile(View view) {
        Intent myProfile = new Intent(HomeClientActivity.this, ProfileActivity.class);
        startActivity(myProfile);
    }

    public void onMyRequests(View view) {
        Intent myRequests = new Intent(HomeClientActivity.this, MyRequestsActivity.class);
        startActivity(myRequests);
    }
}
