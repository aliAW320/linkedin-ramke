package org.linkedin.Models;

import org.linkedin.DB.DBSetup;
import org.linkedin.DB.UniqueID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TextPost{
    private long id;
    private int likes;
    private long userID;
    private String text;

    public TextPost() {
    }

    public TextPost(long id, int likes, long userID, String text) {
        this.id = id;
        this.likes = likes;
        this.userID = userID;
        this.text = text;
    }

    public void editText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    public void insertDB(){
        this.id = UniqueID.generateUniqueID();
        try {
            Connection connection = DBSetup.connect();
            String sql = "INSERT INTO TextPost (id,userID,text) VALUES (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.setLong(2,userID);
            pstmt.setString(3,text);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<TextPost> getUserPosts(){
        List<TextPost> textPosts = new ArrayList<TextPost>();
        try (Connection conn = DBSetup.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM TextPost WHERE userID = ?")) {
            long desiredUserId = 123456; // Replace with the desired user ID
            stmt.setLong(1, desiredUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    textPosts.add(new TextPost(rs.getLong("id"),Like.numberOfLikesPost(rs.getString("id")),rs.getLong("userID"),rs.getString("text")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return textPosts;
    }
    public List<TextPost> getFollowingPosts() {
        List<TextPost> followingPosts = new ArrayList<>();
        try  {
            PreparedStatement stmt = DBSetup.connect().prepareStatement(
                    "SELECT tp.id, tp.text, tp.likes, tp.userID " +
                            "FROM TextPost tp " +
                            "INNER JOIN Follows f ON tp.userID = f.following " +
                            "WHERE f.follower = ?");
            stmt.setLong(1, this.userID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    TextPost post = new TextPost(
                            rs.getLong("id"),
                            rs.getInt("likes"),
                            rs.getLong("userID"),
                            rs.getString("text")
                    );
                    post.setLikes(Like.numberOfLikesPost(String.valueOf(post.id)));
                    followingPosts.add(post);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followingPosts;
    }



    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
