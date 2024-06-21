package com.dana;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionItems {

    private int subscription;
    private int item;
    private int quantity;
    private int price;
    private int amount;

    @JsonProperty("subscription")
    public int getSubscription() {
        return subscription;
    }

    @JsonProperty("subscription")
    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    @JsonProperty("item")
    public int getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(int item) {
        this.item = item;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(int price) {
        this.price = price;
    }

    @JsonProperty("amount")
    public int getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
