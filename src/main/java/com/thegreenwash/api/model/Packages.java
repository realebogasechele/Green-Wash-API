package com.thegreenwash.api.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


public class Packages {
    @Id
    private String packageID;

    @Field
    private String promotionID;
    @Field
    private String packageName;
    @Field
    private double standardPrice;
    @Field
    private double suvPrice;
    @Field
    private String description;


    public Packages(String packageID, String promotionID, String packageName,
                    double standardPrice, double suvPrice, String description)
    {
        this.packageID = packageID;
        this.promotionID = promotionID;
        this.packageName = packageName;
        this.standardPrice = standardPrice;
        this.suvPrice = suvPrice;
        this.description = description;
    }

    //Getters
    public String getPackageID(){
        return this.packageID;
    }

    public String getPromotionID(){
        return this.promotionID;
    }

    public String getPackageName(){
        return this.packageName;
    }

    public double getStandardPrice(){
        return this.standardPrice;
    }

    public double getSuvPrice(){
        return this.suvPrice;
    }

    public String getDescription(){
        return this.description;
    }



    //Setters
    public void setPackageID(String packageID){
        this.packageID = packageID;
    }

    public void setPromotionID(String promotionID){
        this.promotionID = promotionID;
    }

    public void setPackageName(String packageName){
        this.packageID = packageName;
    }

    public void setStandardPrice(float standardPrice){
        this.standardPrice = standardPrice;
    }

    public void setSuvPrice(float suvPrice){
        this.suvPrice = suvPrice;
    }

    public void setDescription(String description){
        this.description = description;
    }







}
