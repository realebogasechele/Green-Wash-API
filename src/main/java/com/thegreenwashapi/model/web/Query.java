package com.thegreenwashapi.model.web;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Query {
    @Id
    private String id;

    @Field
    private String name;
    @Field
    private String surname;
    @Field
    private String email;
    @Field
    private String cellNo;
    @Field
    private String query;
    @Field
    private Boolean newQuery;
    @Field
    private String responseTitle;
    @Field
    private String response;
    @Field
    private String answeredBy;

    public Query() {
    }

    public Query(String name, String surname, String email,
                 String cellNo, String query, Boolean newQuery,
                 String responseTitle, String response, String answeredBy) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cellNo = cellNo;
        this.query = query;
        this.newQuery = newQuery;
        this.responseTitle = responseTitle;
        this.response = response;
        this.answeredBy = answeredBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Boolean getNewQuery() {
        return newQuery;
    }

    public void setNewQuery(Boolean newQuery) {
        this.newQuery = newQuery;
    }

    public String getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(String answeredBy) {
        this.answeredBy = answeredBy;
    }

    public String getResponseTitle() {
        return responseTitle;
    }

    public void setResponseTitle(String responseTitle) {
        this.responseTitle = responseTitle;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
