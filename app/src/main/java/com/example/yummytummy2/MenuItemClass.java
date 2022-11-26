package com.example.yummytummy2;

import com.google.firebase.auth.FirebaseAuth;

public class MenuItemClass {
    String itemName,itemId,resId ;
    int price,quantity;
    String url = "";
    boolean isAdded = false;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(boolean added) {
        isAdded = added;
    }

    public MenuItemClass(String itemName, String itemId, int price,String url) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.price = price;
        this.resId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public MenuItemClass(String itemName, String itemId, int price, int quantity, boolean isAdded) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.isAdded = isAdded;
    }

    public MenuItemClass() {
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

    @Override
    public String toString() {
        return "MenuItemClass{" +
                "itemName='" + itemName + '\'' +
                ", itemId='" + itemId + '\'' +
                ", resId='" + resId + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", isAdded=" + isAdded +
                '}';
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

