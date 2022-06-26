package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

public class OpaySetting {

    private String merchantId;
    @SerializedName("secretkey")
    private String secretKey;
    @SerializedName("publickey")
    private String publicKey;
    private boolean mode;
    private String Callbackurl;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public String getCallbackurl() {
        return Callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        Callbackurl = callbackurl;
    }
}
