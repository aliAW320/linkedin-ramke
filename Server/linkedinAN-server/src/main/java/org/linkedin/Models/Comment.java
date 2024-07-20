package org.linkedin.Models;

import org.linkedin.DB.DBSetup;
import org.linkedin.DB.UniqueID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {
    private String id;
    private String comment;
    private String userID;
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
    public void addComment() {
        try {
            String sql = "INSERT INTO Comment (id, comment, userId, date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setLong(1, UniqueID.generateUniqueID());
            statement.setString(2, this.comment);
            statement.setString(3, this.userID);
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteComment() {
        try {
            String sql = "DELETE FROM Comment WHERE id = ?";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, this.id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Comment> getPostComments(long postId) {
        List<Comment> comments = new ArrayList<>();

        try {
            String sql = "SELECT id, comment, userId, date FROM Comment WHERE userId = ? ORDER BY date DESC";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setLong(1, postId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getString("id"));
                comment.setComment(resultSet.getString("comment"));
                comment.setUserID(resultSet.getString("userId"));
                comment.setDate(new Date(resultSet.getTimestamp("date").getTime()));
                comments.add(comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

}
