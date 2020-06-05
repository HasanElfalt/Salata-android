package com.ar.salata.repositories.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ResponseMessage {
    @SerializedName(value = "message", alternate = {"company_name_ar"})
    private String message1;
    @SerializedName(value = "company_name_en")
    private String message2;

    public String getCompanyArabicName() {
        return message1;
    }

    public String getCompanyEnglishName() {
        return message2;
    }

    @NonNull
    @Override
    public String toString() {
        return message1;
    }
}
