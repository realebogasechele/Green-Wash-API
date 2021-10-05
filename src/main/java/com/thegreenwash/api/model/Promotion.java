package com.thegreenwash.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Promotion{
    @Id
    private String promotionId;

    @Field
    private String packageId;
    @Field
    private String standardPrice;
    @Field
    private String suvPrice;
    @Field
    private boolean isEnabled;

   public Promotion() {
    }

    public Promotion(String packageId, String standardPrice, String suvPrice, boolean isEnabled) {
        this.packageId = packageId;
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


    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
