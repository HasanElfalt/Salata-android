package com.ar.salata.repositories.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DeliveryDate extends RealmObject {
    private RealmList<Shift> shifts;

    private String day;

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(RealmList<Shift> shifts) {
        this.shifts = shifts;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
