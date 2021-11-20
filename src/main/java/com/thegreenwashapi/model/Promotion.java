package com.thegreenwashapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Promotion{
    @Id
    private String promotionId;

    @Field
    private String promotionName;
    @Field
    private String packageName;
    @Field
    private String standardPrice;
    @Field
    private String suvPrice;
    @Field
    private boolean isEnabled;

   public Promotion() {
    }

    public Promotion(String promotionName, String packageName, String standardPrice, String suvPrice, boolean isEnabled) {
        this.promotionName = promotionName;
        this.packageName = packageName;
        this.standardPrice = standardPrice;
        this.suvPrice = suvPrice;
        this.isEnabled = isEnabled;
    }

    public String getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(String standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getSuvPrice() {
        return suvPrice;
    }

    public void setSuvPrice(String suvPrice) {
        this.suvPrice = suvPrice;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
