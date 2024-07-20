package com.example.test.Models;

import com.example.test.Post;

import java.util.List;

public class ResponseSpecial {
    private final boolean result;
    private final String message;
    private int code;
    private String token;
    private String object;
    private User user;
    private List<Post> listPost;
    private Education education;

    public User getUser() {
        return user;
    }

    public ResponseSpecial(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseSpecial(boolean result, String message, int code) {
        this.result = result;
        this.message = message;
        this.code = code;
    }

    public ResponseSpecial(boolean result, String message, int code, String token) {
        this.result = result;
        this.message = message;
        this.code = code;
        this.token = token;
    }

    public ResponseSpecial(boolean result, String message, String object) {
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public boolean is() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public boolean isResult() {
        return result;
    }

    public String getObject() {
        return object;
    }

    public List<Post> getListPost() {
        return listPost;
    }

    public Education getEducation() {
        return education;
    }
}