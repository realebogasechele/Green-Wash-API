package com.thegreenwash.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalTime;
import java.util.List;

@Document
public class Complex {
    @Id
    private String complexId;

    @Field
    private String complexName;
    @Field
    private String address;
    @Field
    private String telephoneNum;
    @Field
    private LocalTime startTime;
    @Field
    private LocalTime endTime;
    @Field
    private String cellNum;
    @Field
    private List<String> units;
    @Field
    private List<String> agents;

    public Complex() {
    }

    public Complex(String complexName, String address, String telephoneNum,
                   LocalTime startTime, LocalTime endTime, String cellNum,
                   List<String> units, List<String> agents) {
        this.complexName = complexName;
        this.address = address;
        this.telephoneNum = telephoneNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cellNum = cellNum;
        this.units = units;
        this.agents = agents;
    }

    public List<String> getAgents() {
        return agents;
    }

    public void setAgents(List<String> agents) {
        this.agents = agents;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getCellNum() {
        return cellNum;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }
}
