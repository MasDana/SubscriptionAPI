package com.dana;

import com.dana.connectionDatabase;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cards {
    private int id;
    private int customer;
    private String cardType;
    private String maskedNumber;
    private int expiryMonth;
    private int expiryYear;
    private String status;
    private int isPrimary;

    public int getId() {
        return id;
    }

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

    @JsonProperty("card_type")
    public String getCardType() {
        return cardType;
    }

    @JsonProperty("card_type")
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @JsonProperty("masked_number")
    public String getMaskedNumber() {
        return maskedNumber;
    }

    @JsonProperty("masked_number")
    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    @JsonProperty("expiry_month")
    public int getExpiryMonth() {
        return expiryMonth;
    }

    @JsonProperty("expiry_month")
    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    @JsonProperty("expiry_year")
    public int getExpiryYear() {
        return expiryYear;
    }

    @JsonProperty("expiry_year")
    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("is_primary")
    public int getIsPrimary() {
        return isPrimary;
    }

    @JsonProperty("is_primary")
    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public static boolean deleteNonPrimaryCard(int customerId, int cardId) {
        String selectQuery = "SELECT is_primary FROM cards WHERE id = ? AND customer = ?";
        String deleteQuery = "DELETE FROM cards WHERE id = ? AND customer = ?";

        try (Connection conn = connectionDatabase.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setInt(1, cardId);
            selectStmt.setInt(2, customerId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int isPrimary = rs.getInt("is_primary");
                if (isPrimary == 0) {
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                        deleteStmt.setInt(1, cardId);
                        deleteStmt.setInt(2, customerId);
                        int rowsAffected = deleteStmt.executeUpdate();
                        return rowsAffected > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
