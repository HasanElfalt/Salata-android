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
    private String facebookAccount;
    private String twitterAccount;
    private String InstagramAccount;
    private String facebookAccountWeb;
    private String twitterAccountWeb;
    private String InstagramAccountWeb;
    private String aboutUs;
    private String contactUs;
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

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public String getInstagramAccount() {
        return InstagramAccount;
    }

    public void setInstagramAccount(String instagramAccount) {
        InstagramAccount = instagramAccount;
    }

    public String getFacebookAccountWeb() {
        return facebookAccountWeb;
    }

    public void setFacebookAccountWeb(String facebookAccountWeb) {
        this.facebookAccountWeb = facebookAccountWeb;
    }

    public String getTwitterAccountWeb() {
        return twitterAccountWeb;
    }

    public void setTwitterAccountWeb(String twitterAccountWeb) {
        this.twitterAccountWeb = twitterAccountWeb;
    }

    public String getInstagramAccountWeb() {
        return InstagramAccountWeb;
    }

    public void setInstagramAccountWeb(String instagramAccountWeb) {
        InstagramAccountWeb = instagramAccountWeb;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }
}
