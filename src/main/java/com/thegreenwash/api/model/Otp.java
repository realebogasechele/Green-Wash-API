package com.thegreenwash.api.model;

import java.time.ZonedDateTime;

public class Otp {
    private String id;

    private String clientId;
    private Integer otpNumber;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    public Otp(){}

    public Otp(String clientId, Integer otpNumber, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.clientId = clientId;
        this.otpNumber = otpNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
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
