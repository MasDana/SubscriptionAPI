package com.dana;

public class Subscriptions {
    private int id;
    private int customer;
    private int billingPeriod;
    private String billingPeriodUnit;
    private int totalDue;
    private String activatedAt;
    private String currentTermStart;
    private String currentTermEnd;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public String getBillingPeriodUnit(){
        return billingPeriodUnit;
    }

    public void setBillingPeriodUnit(String billingPeriodUnit){
        this.billingPeriodUnit = billingPeriodUnit;
    }

    public int getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(int totalDue) {
        this.totalDue = totalDue;
    }

    public String getActivatedAt(){
        return  activatedAt;
    }

    public void setActivatedAt(String activatedAt){
        this.activatedAt = activatedAt;
    }

    public String getCurrentTermStart(){
        return  currentTermStart;
    }

    public void setCurrentTermStart(String CurrentTermStart){
        this.currentTermStart = currentTermStart;
    }

    public String getCurrentTermEnd(){
        return  currentTermEnd;
    }

    public void setCurrentTermEnd(String CurrentTermEnd){
        this.currentTermEnd = currentTermEnd;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
