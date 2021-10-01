package com.thegreenwash.api.model;

public class Otp {
    private String id;

    private String clientId;
    private Integer otpNumber;

    public Otp(){}

    public Otp(String clientId, Integer otpNumber) {
        this.clientId = clientId;
        this.otpNumber = otpNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getOtpNumber() {
        return otpNumber;
    }

    public void setOtpNumber(Integer otpNumber) {
        this.otpNumber = otpNumber;
    }
}
