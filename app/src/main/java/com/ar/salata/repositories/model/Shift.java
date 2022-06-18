package com.ar.salata.repositories.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import io.realm.RealmObject;

public class Shift extends RealmObject {
    private int id;
    private String from;
    private String to;

    private Pivot pivot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        String result = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            result = LocalTime.parse(from, DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("hh:mm:ss a", new Locale("ar")));
        }

        return result;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        String result = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            result = LocalTime.parse(to, DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("hh:mm:ss a", new Locale("ar")));
        }
        return result;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
}
