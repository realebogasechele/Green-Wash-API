package com.thegreenwash.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Booking {
    @Id
    private String bookingId;

    @Field
    private String clientId;
    @Field
    private String vehicleId;
    @Field
    private String agentId;
    @Field
    private String complexName;
    @Field
    private String packageName;
    @Field
    private String price;
    @Field
    private String paymentMethod;
    @Field
    private String date;
    @Field
    private String startTime;
    @Field
    private String endTime;
    @Field
    private String timeZone;
    @Field
    private Boolean isComplete;
    @Field
    private String reasonForIncomplete;
    @Field
    private Boolean isIncomplete;
    @Field
    private Boolean refund;
    @Field
    private Boolean isRefunded;

    public Booking() {
    }

    public Booking(String clientId, String vehicleId, String agentId,
                   String complexName, String packageName, String price,
                   String paymentMethod, String date, String startTime,
                   String endTime, String timeZone, Boolean isComplete,
                   String reasonForIncomplete, Boolean isIncomplete,
                   Boolean refund, Boolean isRefunded) {
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.agentId = agentId;
        this.complexName = complexName;
        this.packageName = packageName;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
        this.isComplete = isComplete;
        this.reasonForIncomplete = reasonForIncomplete;
        this.isIncomplete = isIncomplete;
        this.refund = refund;
        this.isRefunded = isRefunded;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getReasonForIncomplete() {
        return reasonForIncomplete;
    }

    public void setReasonForIncomplete(String reasonForIncomplete) {
        this.reasonForIncomplete = reasonForIncomplete;
    }

    public Boolean getIncomplete() {
        return isIncomplete;
    }

    public void setIncomplete(Boolean incomplete) {
        isIncomplete = incomplete;
    }

    public Boolean getRefund() {
        return refund;
    }

    public void setRefund(Boolean refund) {
        this.refund = refund;
    }

    public Boolean getRefunded() {
        return isRefunded;
    }

    public void setRefunded(Boolean refunded) {
        isRefunded = refunded;
    }
}
