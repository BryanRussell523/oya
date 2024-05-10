package com.example.oya.Object;

import android.graphics.Bitmap;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotesObject {
    private String noteId;
    private String userId;
    private String title;
    private String content;
    private String imageUrl;
    private int titleBackgroundColor;
    private int contentBackgroundColor;

    private long timeStamp;

    public NotesObject(String noteId, String userId, String title, String content, String imageUrl, int titleBackgroundColor, int contentBackgroundColor, long timeStamp){
        this.noteId = noteId;
        this.userId = userId;
        this.title=title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.titleBackgroundColor = titleBackgroundColor;
        this.contentBackgroundColor = contentBackgroundColor;
        this.timeStamp = timeStamp;


    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getimageUrl() {
        return imageUrl;
    }

    public void setimageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTitleBackgroundColor() {
        return titleBackgroundColor;
    }

    public void setTitleBackgroundColor(int titleBackgroundColor) {
        this.titleBackgroundColor = titleBackgroundColor;
    }
    public int getContentBackgroundColor() {
        return contentBackgroundColor;
    }

    public void setContentBackgroundColor(int contentBackgroundColor) {
        this.contentBackgroundColor = contentBackgroundColor;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
