package com.thegreenwash.api.model;

import java.time.ZonedDateTime;

public class Otp {
    private String id;

    private String clientId;
    private Integer otpNumber;
    private String startTime;
    private String endTime;

    public Otp(){}

    public Otp(String clientId, Integer otpNumber, String startTime, String endTime) {
        this.clientId = clientId;
        this.otpNumber = otpNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
