package com.dana;

import org.json.JSONObject;

public class subscriptionItems {

    private int subscription;
    private int item;
    private int quantity;
    private  int price;
    private int amount;

    public int getSubscription(){
        return subscription;
    }

    public void setSubscription(){
        this.subscription = subscription;
    }

    public int getItem(){
        return item;
    }

    public void setItem(){
        this.item = item;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(){
        this.price = quantity;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(){
        this.price = price;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(){
        this.amount = amount;
    }

    public JSONObject objectJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("subscription", subscription);
        jsonObject.put("item", item);
        jsonObject.put("quantity", quantity);
        jsonObject.put("price", price);
        jsonObject.put("amount", amount);
        return jsonObject;
    }

    public int subscriptionItemParsing(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            subscription = obj.getInt("subscription");
            item = obj.getInt("item");
            quantity = obj.getInt("quantity");
            price = obj.getInt("price");
            amount = obj.getInt("amount");
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

}
