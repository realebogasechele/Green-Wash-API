package com.thegreenwash.api.model;

import java.time.*;
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
    private String complexId;
    @Field
    private String packageId;
    @Field
    private String price;
    @Field
    private String paymentMethod;
    @Field
    private LocalDate date;
    @Field
    private ZonedDateTime startTime;
    @Field
    private ZonedDateTime endTime;
    @Field
    private ZoneId timeZone;
    @Field
    private Boolean isComplete;
    @Field
    private Boolean refund;
    @Field
    private Boolean isRefunded;

    public Booking() {
    }

    public Booking(String clientId, String vehicleId, String agentId,
                   String complexId, String packageId, String price,
                   String paymentMethod, LocalDate date, ZonedDateTime startTime,
                   ZonedDateTime endTime, ZoneId timeZone, Boolean isComplete,
                   Boolean refund, Boolean isRefunded) {
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.agentId = agentId;
        this.complexId = complexId;
        this.packageId = packageId;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
        this.isComplete = isComplete;
        this.refund = refund;
        this.isRefunded = isRefunded;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
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
