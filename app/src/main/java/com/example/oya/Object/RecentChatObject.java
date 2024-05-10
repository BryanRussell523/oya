package com.example.oya.Object;

public class RecentChatObject {
    private String chatId;
    private String userId; // This could be the ID of the current user
    private String otherUserId; // This could be the ID of the other user in the chat
    private String lastMessage;
    private long timestamp;

    private String profileImageUrl;
    private String phoneNumber;


    public RecentChatObject(String chatId, String userId, String otherUserId, String lastMessage, long timestamp, String profileImageUrl, String phoneNumber) {
        this.chatId = chatId;
        this.userId = userId;
        this.otherUserId = otherUserId;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.profileImageUrl = profileImageUrl;
        this.phoneNumber = phoneNumber;
    }

    public RecentChatObject() {
        // Default constructor required for Firebase
    }

    // Getters and setters
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
