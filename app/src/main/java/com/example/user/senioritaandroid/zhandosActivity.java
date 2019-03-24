package com.example.user.senioritaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.user.senioritaandroid.Client.CurrentRequestsActivity;

public class ZhandosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhandos);
    }

    public void onCurrentRequest(View view) {
        Intent getVerified = new Intent(ZhandosActivity.this, CurrentRequestsActivity.class);
        startActivity(getVerified);
    }
}
