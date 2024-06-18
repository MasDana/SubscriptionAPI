package com.dana;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public void insertSubscriptions() {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "INSERT INTO subscriptions (customer, billing_period, billing_period_unit, total_due, activated_at, current_term_start, current_term_end, status) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customer);
            pstmt.setInt(2, billingPeriod);
            pstmt.setString(3, billingPeriodUnit);
            pstmt.setInt(4, totalDue);
            pstmt.setString(5, activatedAt);
            pstmt.setString(6, currentTermStart);
            pstmt.setString(7, currentTermEnd);
            pstmt.setString(8, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateSubscriptions(String idSubscriptions) {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "UPDATE subscription SET customer = \"" + customer +
                    "\" , last_name = \"" + billingPeriod +
                    "\" , email = \"" + billingPeriodUnit +
                    "\" , phone_number = \"" + totalDue +
                    "\" , last_name = \"" +activatedAt +
                    "\" , email = \"" + currentTermStart +
                    "\" , phone_number = \"" + currentTermEnd +
                    "\" , phone_number = \"" + status +
                    "\" WHERE users = " + idSubscriptions;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
