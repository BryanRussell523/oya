package com.example.oya.Object;

public class UserObject {
    private String uid;
    private String phoneNumber;
    private String username;
    private String description;
    private String imageUri;
    private boolean profileSet;

    public UserObject() {
        // Default constructor required for Firebase deserialization
    }

    // Constructor with all fields
    public UserObject(String uid, String phoneNumber, String username, String imageUri, String description) {
        this.uid = uid;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isProfileSet() {
        return profileSet;
    }

    public void setProfileSet(boolean profileSet) {
        this.profileSet = profileSet;
    }
}
