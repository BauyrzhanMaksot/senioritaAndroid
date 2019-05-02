package com.example.user.senioritaandroid.Client.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.senioritaandroid.Extra.Order;
import com.example.user.senioritaandroid.R;

import java.util.List;

public class OfferAcceptedListAdapter extends ArrayAdapter<Order> {

    private Context context;
    private int resource;

    public OfferAcceptedListAdapter(Context context, int resource, List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Long  id = getItem(position).getId();
        String pointA = getItem(position).getPointA();
        String pointB = getItem(position).getPointB();
        String price = getItem(position).getPrice();
        //User driver = getItem(position).getDriver();
        //Offer offer = new Offer(id, pointA, pointB, price, driver);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        TextView priceView = (TextView) convertView.findViewById(R.id.priceAcceptedClient);
        TextView pointAView = (TextView) convertView.findViewById(R.id.pointAAcceptedClient);
        TextView pointBView = (TextView) convertView.findViewById(R.id.pointBAcceptedClient);
        pointAView.setText(pointA);
        pointBView.setText(pointB);
        priceView.setText(price);
        return convertView;
    }
}
