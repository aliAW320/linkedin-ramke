package org.linkedin.Models;

import org.linkedin.DB.DBSetup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Follow {
    private String user;
    private String otherUser;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void changeFollowing() {
        try {
            PreparedStatement stmt;
            String checkQuery = "SELECT * FROM Follows WHERE follower = ? AND following = ?";
            stmt = DBSetup.connect().prepareStatement(checkQuery);
            stmt.setString(1, user);
            stmt.setString(2, otherUser);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String deleteQuery = "DELETE FROM Follows WHERE follower = ? AND following = ?";
                stmt = DBSetup.connect().prepareStatement(deleteQuery);
                stmt.setString(1, user);
                stmt.setString(2, otherUser);
                stmt.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO Follows (follower, following) VALUES (?, ?)";
                stmt = DBSetup.connect().prepareStatement(insertQuery);
                stmt.setString(1, user);
                stmt.setString(2, otherUser);
                stmt.executeUpdate();
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getNumberOfFollowers() {
        int numFollowers = 0;
        try {
            PreparedStatement stmt;
            String query = "SELECT COUNT(*) AS num_followers FROM Follows WHERE following = ?";
            stmt = DBSetup.connect().prepareStatement(query);
            stmt.setString(1, otherUser);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                numFollowers = rs.getInt("num_followers");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numFollowers;
    }
    public List<String> getFollowing() {
        List<String> following = new ArrayList<>();
        try {
            PreparedStatement stmt;
            String query = "SELECT following FROM Follows WHERE follower = ?";
            stmt = DBSetup.connect().prepareStatement(query);
            stmt.setString(1, otherUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                following.add(rs.getString("following"));
            }

            rs.close();
            stmt.close( );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return following;
    }

    public List<String> getFollowers() {
        List<String> followers = new ArrayList<>();
        try {
            PreparedStatement stmt;
            String query = "SELECT follower FROM Follows WHERE following = ?";
            stmt = DBSetup.connect().prepareStatement(query);
            stmt.setString(1, otherUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                followers.add(rs.getString("follower"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followers;
    }

}
