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

    public int getCustomer(){ return customer; }

    public void setCustomer(){ this.customer = customer; }

   public String getCardType(){ return cardType;}

    public void setCardType (){this.cardType = cardType;}

    public String getMaskedNumber(){return maskedNumber;}

    public void setMaskedNumber(){this.maskedNumber = maskedNumber;}

    public int getExpiryMonth(){return  expiryMonth;}

    public void setExpiryMonth(){this.expiryMonth = expiryMonth;}

    public int getExpiryYear(){return  expiryYear;}

    public void setExpiryYear(){this.expiryYear = expiryYear;}

    public String getStatus(){return status;}

    public void setStatus(){this.status = status;}

    public int getIsPrimary(){return  isPrimary;}

    public  void setIsPrimary(){this.isPrimary = isPrimary;}