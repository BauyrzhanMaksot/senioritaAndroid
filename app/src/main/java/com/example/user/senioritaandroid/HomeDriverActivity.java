package com.example.user.senioritaandroid;

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
import com.example.user.senioritaandroid.Driver.Activity.AcceptedRequestsActivity;
import com.example.user.senioritaandroid.Driver.Activity.CurrentRequestsActivity;
import com.example.user.senioritaandroid.Driver.Activity.HistoryActivity;
import com.example.user.senioritaandroid.Driver.Activity.MakeOfferActivity;
import com.example.user.senioritaandroid.Driver.Activity.MyOffersActivity;
import com.example.user.senioritaandroid.User.ProfileActivity;

public class HomeDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        SpringBootWebSocketClient client = new SpringBootWebSocketClient();
        TopicHandler handler = client.subscribe("/bake/driver");
        handler.addListener(new StompMessageListener() {
            @Override
            public void onMessage(StompMessage message) {
                System.out.println(message.getHeader("destination") + ": " + message.getContent());
                Intent intent;
                if (message.getContent().contains("id=")) {
                    intent = new Intent(HomeDriverActivity.this, AcceptedOffersActivity.class);
                } else {
                    intent = new Intent(HomeDriverActivity.this, CurrentOffersActivity.class);
                }
                TaskStackBuilder t = TaskStackBuilder.create(HomeDriverActivity.this);
                t.addParentStack(HomeClientActivity.class);
                t.addNextIntent(intent);
                PendingIntent p = t.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification n = new Notification.Builder(HomeDriverActivity.this)
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

    public void onMakeOffer(View view) {
        Intent makeOffer = new Intent(HomeDriverActivity.this, MakeOfferActivity.class);
        startActivity(makeOffer);
    }
}
