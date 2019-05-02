package com.example.user.senioritaandroid.Client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.user.senioritaandroid.Client.Activity.AcceptedOffersActivity;
import com.example.user.senioritaandroid.Client.Activity.CurrentOffersActivity;
import com.example.user.senioritaandroid.Client.Activity.HistoryClientActivity;
import com.example.user.senioritaandroid.Client.Activity.MakeRequestActivity;
import com.example.user.senioritaandroid.Client.Activity.MyRequestsActivity;
import com.example.user.senioritaandroid.Extra.Constant;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.User.ProfileActivity;
import com.example.user.senioritaandroid.WebSocket.SpringBootWebSocketClient;
import com.example.user.senioritaandroid.WebSocket.StompMessage;
import com.example.user.senioritaandroid.WebSocket.StompMessageListener;
import com.example.user.senioritaandroid.WebSocket.TopicHandler;

public class HomeClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
        SpringBootWebSocketClient client = new SpringBootWebSocketClient();
        TopicHandler handler = client.subscribe("/bake/client");
        handler.addListener(new StompMessageListener() {
            @Override
            public void onMessage(StompMessage message) {
                System.out.println(message.getHeader("destination") + ": " + message.getContent());
                Intent intent;
                if (message.getContent().contains("id=")) {
                    intent = new Intent(HomeClientActivity.this, AcceptedOffersActivity.class);
                } else {
                    intent = new Intent(HomeClientActivity.this, CurrentOffersActivity.class);
                }
                TaskStackBuilder t = TaskStackBuilder.create(HomeClientActivity.this);
                t.addParentStack(HomeClientActivity.class);
                t.addNextIntent(intent);
                PendingIntent p = t.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification n = new Notification.Builder(HomeClientActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Ticocar")
                        .setContentText(message.getContent())
                        .setAutoCancel(true)
                        .setContentIntent(p)
                        .build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0, n);
                Vibrator vib = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE);
                vib.vibrate(500);
            }
        });
        client.connect(Constant.ws + "bake/ws/websocket");
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

    public void onMakeRequest(View view) {
        Intent makeRequest = new Intent(HomeClientActivity.this, MakeRequestActivity.class);
        startActivity(makeRequest);
    }
}
