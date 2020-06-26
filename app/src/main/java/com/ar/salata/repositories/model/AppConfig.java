package com.ar.salata.repositories.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AppConfig extends RealmObject {
    @PrimaryKey
    private int id = 1;
    private String companyNameAr;
    private String companyNameEn;
    private double minimumPurchases;
    private RealmList<String> phones = new RealmList<>();


    public String getCompanyNameAr() {
        return companyNameAr;
    }

    public void setCompanyNameAr(String companyNameAr) {
        this.companyNameAr = companyNameAr;
    }

    public String getCompanyNameEn() {
        return companyNameEn;
    }

    public void setCompanyNameEn(String companyNameEn) {
        this.companyNameEn = companyNameEn;
    }

    public List<String> getPhones() {
        return new ArrayList<>(phones);
    }

    public void setPhones(List<String> phones) {
        this.phones.clear();
        this.phones.addAll(phones);
    }

    public double getMinimumPurchases() {
        return minimumPurchases;
    }

    public void setMinimumPurchases(double minimumPurchases) {
        this.minimumPurchases = minimumPurchases;
    }
}
