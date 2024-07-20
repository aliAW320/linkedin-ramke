package org.linkedin.Models;

import org.linkedin.DB.DBSetup;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    private String id;
    private String senderId;
    private String receiverId;
    private String message;
    private LocalDateTime timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void addMessage() {
        try {
            String sql = "INSERT INTO Chat (id, senderId, receiverId, message) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, this.id);
            statement.setString(2, this.senderId);
            statement.setString(3, this.receiverId);
            statement.setString(4, this.message);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Chat> getOrderMessageBetweenTwo() {
        List<Chat> messages = new ArrayList<>();

        try {
            String sql = "SELECT id, senderId, receiverId, message, timestamp " +
                    "FROM Chat " +
                    "WHERE (senderId = ? AND receiverId = ?) " +
                    "   OR (senderId = ? AND receiverId = ?) " +
                    "ORDER BY timestamp DESC";

            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, this.senderId);
            statement.setString(2, this.receiverId);
            statement.setString(3, this.receiverId);
            statement.setString(4, this.senderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Chat chat = new Chat();
                chat.setId(resultSet.getString("id"));
                chat.setSenderId(resultSet.getString("senderId"));
                chat.setReceiverId(resultSet.getString("receiverId"));
                chat.setMessage(resultSet.getString("message"));
                chat.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
                messages.add(chat);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

}
