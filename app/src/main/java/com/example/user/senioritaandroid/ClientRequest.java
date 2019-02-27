package com.example.user.senioritaandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientRequest {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("point_a")
    private String pointA;

    @Expose
    @SerializedName("point_b")
    private String pointB;

    @Expose
    @SerializedName("price")
    private String price;

    @Expose
    @SerializedName("client_id")
    private User client;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointA() {
        return pointA;
    }

    public void setPointA(String pointA) {
        this.pointA = pointA;
    }

    public String getPointB() {
        return pointB;
    }

    public void setPointB(String pointB) {
        this.pointB = pointB;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
