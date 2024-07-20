package com.example.test;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class Post {
    private String user;
    private String text;
    private String imageUrl;
    private String videoUrl;
    private boolean liked;
    private int likes = 0;
    private int dislikes = 0;
    private ArrayList<Comment> comment = new ArrayList<>();
    private String id;
    private String userID;



    public Post(String user, String text, String imageUrl, String videoUrl) {
        this.user = user;
        this.text = text;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.liked = false; // Default not liked
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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getLikes() {
        return likes;
    }

    public String getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }
}
