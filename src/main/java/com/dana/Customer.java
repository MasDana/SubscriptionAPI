package com.dana;

import org.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Customer {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public JSONObject objectJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", id);
        jsonObject.put("first_name", firstName);
        jsonObject.put("last_name", lastName);
        jsonObject.put("email", email);
        jsonObject.put("phone_number", phoneNumber);
        return jsonObject;
    }

    public int customerParse(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            firstName = obj.getString("first_name");
            lastName = obj.getString("last_name");
            email = obj.getString("email");
            phoneNumber = obj.getString("phone_number");
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }
    public void insertUser() {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "INSERT INTO customer (first_name, last_name, email, phone_number) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(String idUser) {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "UPDATE customer SET first_name = \"" + firstName +
                    "\" , last_name = \"" + lastName +
                    "\" , email = \"" + email +
                    "\" , phone_number = \"" + phoneNumber +
                    "\" WHERE users = " + idUser;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

