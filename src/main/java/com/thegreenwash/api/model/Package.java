package com.thegreenwash.api.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


public class Package {
    @Id
    private String packageId;

    @Field
    private String promotionId;
    @Field
    private String packageName;
    @Field
    private long minutes;
    @Field
    private double standardPrice;
    @Field
    private double suvPrice;
    @Field
    private String description;
    @Field
    private Boolean onPromotion;

    public Package() {
    }

    public Package(String promotionId, String packageName, long minutes,
                   double standardPrice, double suvPrice,
                   String description, Boolean onPromotion) {
        this.promotionId = promotionId;
        this.packageName = packageName;
        this.minutes = minutes;
        this.standardPrice = standardPrice;
        this.suvPrice = suvPrice;
        this.description = description;
        this.onPromotion = onPromotion;
    }

    //Getters
    public String getPackageId() {
        return packageId;
    }

    public String getPromotionId() {
        return promotionId;
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

    public long getMinutes() {
        return minutes;
    }

    public Boolean getOnPromotion() {
        return onPromotion;
    }

    //Setters
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    public void setStandardPrice(double standardPrice){
        this.standardPrice = standardPrice;
    }

    public void setSuvPrice(double suvPrice){
        this.suvPrice = suvPrice;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public void setOnPromotion(Boolean onPromotion) {
        this.onPromotion = onPromotion;
    }
}
