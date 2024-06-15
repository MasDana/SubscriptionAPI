package com.dana;

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
}
