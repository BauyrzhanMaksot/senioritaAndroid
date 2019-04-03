package com.example.user.senioritaandroid.Driver.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.senioritaandroid.Client.Request;
import com.example.user.senioritaandroid.Order;
import com.example.user.senioritaandroid.R;
import com.example.user.senioritaandroid.User.User;

import java.util.List;

public class HistoryDriverAdapter extends ArrayAdapter<Order> {

    private Context context;
    private int resourse;

    public HistoryDriverAdapter(Context context, int resource, List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Long  id = getItem(position).getId();
        String pointA = getItem(position).getPointA();
        String pointB = getItem(position).getPointB();
        String price = getItem(position).getPrice();
        User client = getItem(position).getClient();
        Request request = new Request(id, pointA, pointB, price, client);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resourse, parent, false);

        TextView priceView = (TextView) convertView.findViewById(R.id.priceHistory);
        TextView pointAView = (TextView) convertView.findViewById(R.id.pointAHistory);
        TextView pointBView = (TextView) convertView.findViewById(R.id.pointBHistory);
        pointAView.setText(pointA);
        pointBView.setText(pointB);
        priceView.setText(price);
        return convertView;
    }
}
