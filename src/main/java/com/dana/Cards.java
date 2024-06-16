package com.dana;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public int getCustomer() {
        return customer;
    }

    public void setCustomer() {
        this.customer = customer;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType() {
        this.cardType = cardType;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber() {
        this.maskedNumber = maskedNumber;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth() {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear() {
        this.expiryYear = expiryYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus() {
        this.status = status;
    }

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary() {
        this.isPrimary = isPrimary;
    }

    public JSONObject objectJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("customer", customer);
        jsonObject.put("card_type", cardType);
        jsonObject.put("masked_number", maskedNumber);
        jsonObject.put("expiry_month", expiryMonth);
        jsonObject.put("expiry_year", expiryYear);
        jsonObject.put("status", status);
        jsonObject.put("is_primary", isPrimary);
        return jsonObject;
    }

    public int cardsParse(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            customer = obj.getInt("customer");
            cardType = obj.getString("card_type");
            maskedNumber = obj.getString("masked_number");
            expiryMonth = obj.getInt("expiry_month");
            expiryYear = obj.getInt("expiry_year");
            status = obj.getString("status");
            isPrimary = obj.getInt("is_primary");
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    public void insertCards() {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "INSERT INTO cards (customer, card_type, masked_number, expiry_month, expiry_year, status, is_primary) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customer);
            pstmt.setString(2, cardType);
            pstmt.setString(3, maskedNumber);
            pstmt.setInt(4, expiryMonth);
            pstmt.setInt(5, expiryYear);
            pstmt.setString(6, status);
            pstmt.setInt(7, isPrimary);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCards(String id) {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "UPDATE cards SET customer = \"" + customer +
                    "\" , card_type = \"" + cardType +
                    "\" , masked_number = \"" + maskedNumber +
                    "\" , expiry_month = \"" + expiryMonth +
                    "\" , expiry_year = \"" + expiryYear +
                    "\" , status = \"" + status +
                    "\" , is_primary = \"" + isPrimary +
                    "\" WHERE users = " + id;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}