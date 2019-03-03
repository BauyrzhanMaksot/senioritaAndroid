package com.example.user.senioritaandroid.Driver;

import com.example.user.senioritaandroid.User.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Offer {
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
    @SerializedName("driver")
    private User driver;
    @Expose
    @SerializedName("longTerm")
    private Boolean longTerm;
    @Expose
    @SerializedName("seats")
    private Integer seats;
    @Expose
    @SerializedName("time")
    private Date time;

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

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Boolean getLongTerm() {
        return longTerm;
    }

    public void setLongTerm(Boolean longTerm) {
        this.longTerm = longTerm;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Offer(String pointA, String pointB, String price) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", pointA='" + pointA + '\'' +
                ", pointB='" + pointB + '\'' +
                ", price='" + price + '\'' +
                ", driver=" + driver +
                ", longTerm=" + longTerm +
                ", seats=" + seats +
                ", time=" + time +
                '}';
    }
}
