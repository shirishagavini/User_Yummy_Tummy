package com.example.yummytummy2;

public class UserData {
    String isFormFilled;
    String UserId,UserType = "customer";

    public UserData() {
    }

    public UserData(String isFormFilled, String userId) {
        this.isFormFilled = isFormFilled;
        UserId = userId;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "isFormFilled=" + isFormFilled +
                ", UserId='" + UserId + '\'' +
                ", UserType='" + UserType + '\'' +
                '}';
    }

    public String getIsFormFilled() {
        return isFormFilled;
    }

    public void setFormFilled(String formFilled) {
        isFormFilled = formFilled;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
