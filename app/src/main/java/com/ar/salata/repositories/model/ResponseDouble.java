package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDouble {
    @SerializedName(value = "min_purchases", alternate = {"min_time_change_order"})
    private double value;

    public double getValue() {
        return value;
    }
}
