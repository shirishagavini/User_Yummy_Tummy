package com.example.yummytummy2;


import com.google.firebase.auth.FirebaseAuth;

public class MyOrders {
    String itemName,itemId;
    int price;
    int quantity;
    String date,timeStamp,paymentMode;
    String currUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public MyOrders() {
    }

    public MyOrders(String itemName, String itemId, int price, int quantity, String date, String timeStamp, String paymentMode) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.timeStamp = timeStamp;
        this.paymentMode = paymentMode;
        this.currUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCurrUserId() {
        return currUserId;
    }

    public void setCurrUserId(String currUserId) {
        this.currUserId = currUserId;
    }

    @Override
    public String toString() {
        return "OrdersDetails{" +
                "itemName='" + itemName + '\'' +
                ", itemId='" + itemId + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                '}';
    }
}

