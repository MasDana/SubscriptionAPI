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
        jsonObject.put("users", id);
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


}

