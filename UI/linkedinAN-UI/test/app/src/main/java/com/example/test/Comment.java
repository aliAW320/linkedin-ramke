package com.example.test;

import java.util.Date;

public class Comment {
    private String user;
    private String text;
    private String imageUrl;
    private String videoUrl;
    private Date timestamp;
    private int likes = 0;
    private int dislikes = 0;

    public Comment(String user, String text, String imageUrl, String videoUrl, Date timestamp) {
        this.user = user;
        this.text = text;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void addLike() {
        likes++;
    }

    public void addDislike() {
        dislikes++;
    }

    public void removeLike() {
        likes--;
    }

    public void removeDislike() {
        dislikes--;
    }
}
