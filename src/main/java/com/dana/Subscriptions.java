package com.dana;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("customer")
    public int getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(int customer) {
        this.customer = customer;
    }

    @JsonProperty("billing_period")
    public int getBillingPeriod() {
        return billingPeriod;
    }

    @JsonProperty("billing_period")
    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    @JsonProperty("billing_period_unit")
    public String getBillingPeriodUnit() {
        return billingPeriodUnit;
    }

    @JsonProperty("billing_period_unit")
    public void setBillingPeriodUnit(String billingPeriodUnit) {
        this.billingPeriodUnit = billingPeriodUnit;
    }

    @JsonProperty("total_due")
    public int getTotalDue() {
        return totalDue;
    }

    @JsonProperty("total_due")
    public void setTotalDue(int totalDue) {
        this.totalDue = totalDue;
    }

    @JsonProperty("activated_at")
    public String getActivatedAt() {
        return activatedAt;
    }

    @JsonProperty("activated_at")
    public void setActivatedAt(String activatedAt) {
        this.activatedAt = activatedAt;
    }

    @JsonProperty("current_term_start")
    public String getCurrentTermStart() {
        return currentTermStart;
    }

    @JsonProperty("current_term_start")
    public void setCurrentTermStart(String currentTermStart) {
        this.currentTermStart = currentTermStart;
    }

    @JsonProperty("current_term_end")
    public String getCurrentTermEnd() {
        return currentTermEnd;
    }

    @JsonProperty("current_term_end")
    public void setCurrentTermEnd(String currentTermEnd) {
        this.currentTermEnd = currentTermEnd;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public static List<Subscriptions> getSubscriptionsByCustomerIdAndStatus(int customerId, String status) {
        List<Subscriptions> subscriptions = new ArrayList<>();
        try (Connection conn = connectionDatabase.getConnection()) {
            String sql = "SELECT * FROM subscriptions WHERE customer = ? AND status = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setString(2, status);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Subscriptions subs = new Subscriptions();
                subs.setId(rs.getInt("id"));
                subs.setCustomer(rs.getInt("customer"));
                subs.setBillingPeriod(rs.getInt("billing_period"));
                subs.setBillingPeriodUnit(rs.getString("billing_period_unit"));
                subs.setTotalDue(rs.getInt("total_due"));
                subs.setActivatedAt(rs.getString("activated_at"));
                subs.setCurrentTermStart(rs.getString("current_term_start"));
                subs.setCurrentTermEnd(rs.getString("current_term_end"));
                subs.setStatus(rs.getString("status"));
                subscriptions.add(subs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    public static List<Subscriptions> getAllSubscription(String sortBy, String sortType) {
        List<Subscriptions> subscriptions = new ArrayList<>();
        try (Connection conn = connectionDatabase.getConnection()) {
            String sql = "SELECT * FROM subscriptions ORDER BY current_term_end DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Subscriptions subs = new Subscriptions();
                subs.setId(rs.getInt("id"));
                subs.setCustomer(rs.getInt("customer"));
                subs.setBillingPeriod(rs.getInt("billing_period"));
                subs.setBillingPeriodUnit(rs.getString("billing_period_unit"));
                subs.setTotalDue(rs.getInt("total_due"));
                subs.setActivatedAt(rs.getString("activated_at"));
                subs.setCurrentTermStart(rs.getString("current_term_start"));
                subs.setCurrentTermEnd(rs.getString("current_term_end"));
                subs.setStatus(rs.getString("status"));
                subscriptions.add(subs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

}
