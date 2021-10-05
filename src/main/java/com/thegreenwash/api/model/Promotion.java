package com.thegreenwash.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Promotion extends Packages {
    @Id
    private String promotionId;

    @Field
    private String packageId;
    @Field
    private boolean isEnabled;

   public Promotion() {
    }

    public Promotion(String promotionId, String packageId, boolean isEnabled, String packageName, double standardPrice) {
        this.promotionId = promotionId;
        this.packageId = packageId;
        this.isEnabled = isEnabled;
        setPackageName(packageName);
        setStandardPrice(standardPrice);
    }

    public Promotion(String promotionId, String packageId, boolean isEnabled) {
        this.promotionId = promotionId;
        this.packageId = packageId;
        this.isEnabled = isEnabled;
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
