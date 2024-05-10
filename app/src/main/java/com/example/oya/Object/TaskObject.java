package com.example.oya.Object;

import java.util.Date;

public class TaskObject {
    private String taskId;
    private String userId; // Added field for userId
    private String title;
    private String description;
    private Date date;
    private Date startTime;
    private Date endTime;
    private boolean reminder;
    private String category;

    // Default constructor required for Firestore deserialization

    public TaskObject() {
    }

    public TaskObject(String taskId, String userId, String title, String description, Date date, Date startTime, Date endTime, boolean reminder, String category) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reminder = reminder;
        this.category = category;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
