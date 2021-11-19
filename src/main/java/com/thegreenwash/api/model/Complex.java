package com.thegreenwash.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document
public class Complex {
    @Id
    private String complexId;

    @Field
    private String complexName;
    @Field
    private String street1;
    @Field
    private String street2;
    @Field
    private String city;
    @Field
    private String province;
    @Field
    private String postalCode;
    @Field
    private String telephoneNum;
    @Field
    private String startTime;
    @Field
    private String endTime;
    @Field
    private String cellNum;
    @Field
    private List<String> units;
    @Field
    private List<String> agents;

    //Constructor
    public Complex() {
    }

    public Complex(String complexName, String street1, String street2,
                   String city, String province, String postalCode,
                   String telephoneNum, String startTime, String endTime,
                   String cellNum, List<String> units, List<String> agents) {
        this.complexName = complexName;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.telephoneNum = telephoneNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cellNum = cellNum;
        this.units = units;
        this.agents = agents;
    }

    //Getter
    public String getComplexId() {
        return complexId;
    }

    public String getComplexName() {
        return complexName;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCellNum() {
        return cellNum;
    }

    public List<String> getUnits() {
        return units;
    }

    public List<String> getAgents() {
        return agents;
    }

    //Setter

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public void setAgents(List<String> agents) {
        this.agents = agents;
    }
}


