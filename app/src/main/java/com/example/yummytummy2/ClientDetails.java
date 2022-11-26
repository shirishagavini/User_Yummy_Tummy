package com.example.yummytummy2;

public class    ClientDetails {
    private String name;
    private String phone;
    private String pincode;
    private String address;
    private String state;
    private String landmark;
    private String walletBalance = "0";

    public ClientDetails() {
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public ClientDetails(String name, String phone, String pincode, String address, String state, String landmark) {
        this.name = name;
        this.phone = phone;
        this.pincode = pincode;
        this.address = address;
        this.state = state;
        this.landmark = landmark;
    }

    @Override
    public String toString() {
        return "ClientDetails{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pincode='" + pincode + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", landmark='" + landmark + '\'' +
                ", walletBalance=" + walletBalance +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
