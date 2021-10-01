package com.thegreenwash.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Agent {
    @Id
    private String agentId;

    @Field
    private String complexId;
    @Field
    private String contractId;
    @Field
    private String name;
    @Field
    private String surname;
    @Field
    private String cellNum;
    @Field
    private String address;
    @Field
    private String password;

    public Agent() {}

    public Agent(String agentId, String complexId, String contractId,
                 String name, String surname, String cellNum, String address, String password) {
        this.agentId = agentId;
        this.complexId = complexId;
        this.contractId = contractId;
        this.name = name;
        this.surname = surname;
        this.cellNum = cellNum;
        this.address = address;
        this.password = password;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public String getCellNum() {
        return cellNum;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
