package com.example.user.senioritaandroid.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserImage {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("location")
    private String location;

    @Expose
    @SerializedName("user")
    private User user;

    public Long getId() {
        return id;    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", user=" + user +
                '}';
    }
}
