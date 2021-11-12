package com.thegreenwash.api.model.web;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Testimonial {
    @Id
    private String id;

    @Field
    private String name;
    @Field
    private String surname;
    @Field
    private String socialHandle;
    @Field
    private Integer rating;
    @Field
    private String title;
    @Field
    private String description;

    public Testimonial() {
    }

    public Testimonial(String name, String surname, String socialHandle, Integer rating, String title, String description) {
        this.name = name;
        this.surname = surname;
        this.socialHandle = socialHandle;
        this.rating = rating;
        this.title = title;
        this.description = description;
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

    public String getSocialHandle() {
        return socialHandle;
    }

    public void setSocialHandle(String socialHandle) {
        this.socialHandle = socialHandle;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
