package com.dana;

import org.json.JSONObject;

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

    public JSONObject objectJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("customer", customer);
        jsonObject.put("billing_period", billingPeriod);
        jsonObject.put("billing_period_unit", billingPeriodUnit);
        jsonObject.put("total_due", totalDue);
        jsonObject.put("activated_at",activatedAt);
        jsonObject.put("current_term_start",currentTermStart);
        jsonObject.put("current_term_end", currentTermEnd);
        jsonObject.put("status", status);
        return jsonObject;
    }

    public int subscriptionsParsing(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            id = obj.getInt("id");
            customer = obj.getInt("customer");
            billingPeriod = obj.getInt("billing_period");
            billingPeriodUnit = obj.getString("billing_period_unit");
            totalDue = obj.getInt("total_due");
            activatedAt = obj.getString("activated_at");
            currentTermStart = obj.getString("current_term_start");
            currentTermEnd = obj.getString("current_term_end");
            status = obj.getString("status");
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

}
