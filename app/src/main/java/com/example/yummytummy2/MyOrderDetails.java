package com.example.yummytummy2;

public class MyOrderDetails {
    String itemName,itemId;
    int price;
    int quantity;
    String date,timeStamp,paymentMode;

    public MyOrderDetails() {
    }

    public MyOrderDetails(String itemName, String itemId, int price, int quantity, String date, String timeStamp, String paymentMode) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.timeStamp = timeStamp;
        this.paymentMode = paymentMode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    @Override
    public String toString() {
        return "MyOrderDetails{" +
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
