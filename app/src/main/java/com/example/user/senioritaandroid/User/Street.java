package com.example.user.senioritaandroid.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Street {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("streetName")
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

    @Override
    public String toString() {
        return "Street{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                '}';
    }
}
