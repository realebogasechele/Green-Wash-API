package com.thegreenwash.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Client {
    @Id
    private String clientId;

    @Field
    private String complexId;
    @Field
    private String name;
    @Field
    private String surname;
    @Field
    private String unitNum;
    @Field
    private String cellNum;
    @Field
    private String password;
    @Field
    private String deviceId;
    @Field
    private Integer otp;

    public Client(){}

    public Client(String complexId, String name, String surname, String unitNum, String cellNum, String password, String deviceId, Integer otp) {
        this.complexId = complexId;
        this.name = name;
        this.surname = surname;
        this.unitNum = unitNum;
        this.cellNum = cellNum;
        this.password = password;
        this.deviceId = deviceId;
        this.otp = otp;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

    public String getCellNum() {
        return cellNum;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
