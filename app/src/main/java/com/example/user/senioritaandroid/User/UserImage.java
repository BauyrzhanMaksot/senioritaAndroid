package com.example.user.senioritaandroid.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserImage {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("id")
    private String streetName;


    public Long getId() {
        return id;    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

}
