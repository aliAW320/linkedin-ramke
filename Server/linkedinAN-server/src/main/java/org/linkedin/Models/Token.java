package org.linkedin.Models;

import java.time.LocalDateTime;

public class Token {
    private String token;
    private String userID;
    private LocalDateTime date;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getToken() {
        return token;
    }

    public String getUserID() {
        return userID;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
