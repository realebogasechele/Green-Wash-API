package com.thegreenwashapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Client {
    @Id
    private String clientId;

    @Field
    private String complexName;
    @Field
    private String name;
    @Field
    private String surname;
    @Field
    private String unitNum;
    @Field
    private String cellNum;
    @Field
    private String email;
    @Field
    private String password;
    @Field
    private String deviceId;
    @Field
    private Boolean isDisabled;
    @Field
    private Integer counter;
    @Field
    private Boolean isBlacklisted;

    public Client(){}

    public Client(String complexName, String name, String surname, String unitNum,
                  String cellNum, String email, String password, String deviceId,
                  Boolean isDisabled, Integer counter, Boolean isBlacklisted) {
        this.complexName = complexName;
        this.name = name;
        this.surname = surname;
        this.unitNum = unitNum;
        this.cellNum = cellNum;
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
        this.isDisabled = isDisabled;
        this.counter = counter;
        this.isBlacklisted = isBlacklisted;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Boolean getBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(Boolean blacklisted) {
        isBlacklisted = blacklisted;
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

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
