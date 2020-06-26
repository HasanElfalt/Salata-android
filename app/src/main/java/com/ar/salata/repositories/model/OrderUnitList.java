package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderUnitList {
    @SerializedName("details")
    private List<OrderUnit> units;

    public List<OrderUnit> getUnits() {
        return units;
    }
}
