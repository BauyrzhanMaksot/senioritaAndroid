package com.example.user.senioritaandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DriverOffer {

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
    @SerializedName("driver_id")
    private User driver;

    @Expose
    @SerializedName("is_long_term")
    private Boolean longTerm;

    @Expose
    @SerializedName("seats")
    private Integer seats;

    @Expose
    @SerializedName("ride_time")
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

    public Boolean isLongTerm() {
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
}
