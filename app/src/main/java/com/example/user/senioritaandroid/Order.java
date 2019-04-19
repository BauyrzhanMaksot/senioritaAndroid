package com.example.user.senioritaandroid;

import com.example.user.senioritaandroid.User.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Order {

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
    @SerializedName("date")
    private Date date;


    @Expose
    @SerializedName("clientRating")
    private String clientRating;

    @Expose
    @SerializedName("clientComment")
    private String clientComment;

    @Expose
    @SerializedName("driverRating")
    private String driverRating;

    @Expose
    @SerializedName("driverComment")
    private String driverComment;

    @Expose
    @SerializedName("driver")
    private User driver;

    @Expose
    @SerializedName("client")
    private User client;

    @Expose
    @SerializedName("price")
    private String price;

    @Expose
    @SerializedName("status")
    private Integer status;

    public Long getId() {
        return id;    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClientRating() {
        return clientRating;
    }

    public void setClientRating(String clientRating) {
        this.clientRating = clientRating;
    }

    public String getClientComment() {
        return clientComment;
    }

    public void setClientComment(String clientComment) {
        this.clientComment = clientComment;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getDriverComment() {
        return driverComment;
    }

    public void setDriverComment(String driverComment) {
        this.driverComment = driverComment;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pointA='" + pointA + '\'' +
                ", pointB='" + pointB + '\'' +
                ", date=" + date +
                ", clientRating='" + clientRating + '\'' +
                ", clientComment='" + clientComment + '\'' +
                ", driverRating='" + driverRating + '\'' +
                ", driverComment='" + driverComment + '\'' +
                ", driver=" + driver +
                ", client=" + client +
                ", price='" + price + '\'' +
                ", status=" + status +
                '}';
    }
}
