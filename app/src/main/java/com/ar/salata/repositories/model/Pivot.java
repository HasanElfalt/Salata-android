package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Pivot extends RealmObject {
    @SerializedName("zone_id")
    private int zoneId;
    @SerializedName("shift_id")
    private int shiftId;

    public Pivot() {
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }
}
