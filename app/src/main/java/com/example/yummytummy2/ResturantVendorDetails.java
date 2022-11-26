package com.example.yummytummy2;

public class ResturantVendorDetails {
    String name,url,resturantId;
    Long ratings;
    int pincode;

    public ResturantVendorDetails() {
    }

    public ResturantVendorDetails(String name, String url, String resturantId, Long ratings,int pincode) {
        this.name = name;
        this.url = url;
        this.resturantId = resturantId;
        this.ratings = ratings;
        this.pincode = pincode;
    }

//    public ResturantVendorDetails(String name, String url, String id, Long ratings, Long totalNumber) {
//        this.name = name;
//        this.url = url;
//        this.resturantId = id;
//        this.ratings = ratings;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResturantId() {
        return resturantId;
    }

    public void setResturantId(String resturantId) {
        this.resturantId = resturantId;
    }

    public Long getRatings() {
        return ratings;
    }

    public void setRatings(Long ratings) {
        this.ratings = ratings;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
