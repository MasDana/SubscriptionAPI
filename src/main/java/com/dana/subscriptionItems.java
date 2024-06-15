package com.dana;
CREATE TABLE `subscription_items` (
        `subscription` INTEGER,
        `item` INTEGER,
        `quantity` INTEGER NOT NULL,
        `price` INTEGER NOT NULL,
        `amount` INTEGER NOT NULL,
PRIMARY KEY (`subscription`, `item`)
);
public class subscriptionItems {

    private int subscription;
    private int item;
    private int quantity;
    private  int price;
    private int amount;

    public int getSubscription(){return subscription;}

    public void setSubscription(){this.subscription = subscription;}

    public int getItem(){return item;}

    public void setItem(){this.item = item;}

    public int getQuantity(){return quantity;}

    public void setQuantity(){this.price = quantity;}

    public int getPrice(){return price;}

    public void setPrice(){this.price = price;}

    public int getAmount(){return amount;}

    public void setAmount(){this.amount = amount;}

}
