package com.example.user.senioritaandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Street {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("street_name")
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
