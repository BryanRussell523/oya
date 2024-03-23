package com.example.oya.Object;

import java.io.Serializable;

public class UserObject implements Serializable {
    private String uid;
    private String name;
    private String phone;
    private String username;
    private String description;
    private String profileImageUrl;

    public UserObject() {
        // Default constructor required for Firebase deserialization
    }

    // Constructor with all fields
    public UserObject(String uid, String name, String phone, String username, String description, String profileImageUrl) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phoneNumber) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getprofileImageUrl() {
        return profileImageUrl;
    }

    public void setprofileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
