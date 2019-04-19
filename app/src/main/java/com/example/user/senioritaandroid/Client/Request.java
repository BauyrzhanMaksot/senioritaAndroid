package com.example.user.senioritaandroid.Client;

import com.example.user.senioritaandroid.User.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("pointA")
    private String pointA;
    @Expose
    @SerializedName("pointB")
    private String pointB;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("client")
    private User client;

    public Request(Long id, String pointA, String pointB, String price, User client) {
        this.id = id;
        this.pointA = pointA;
        this.pointB = pointB;
        this.price = price;
        this.client = client;
    }

    public Request(String pointA, String pointB, String price) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.price = price;
    }

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

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", pointA='" + pointA + '\'' +
                ", pointB='" + pointB + '\'' +
                ", price='" + price + '\'' +
                ", client=" + client +
                '}';
    }
}
