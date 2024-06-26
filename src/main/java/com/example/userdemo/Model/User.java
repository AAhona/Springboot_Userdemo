package com.example.userdemo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User
{
    @Id
    private String userId;
    private String userName;
    private String email;
    private String phoneNumber;


    //Getter and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //Empty constructor
    public User() {
    }

    //Constructor with all the variables
    public User(String userId, String userName, String email, String phoneNumber) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}


