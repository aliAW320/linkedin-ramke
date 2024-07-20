package org.linkedin.Models;

import java.sql.*;
import org.linkedin.DB.DBSetup;
import org.linkedin.DB.UniqueID;
public class LoginRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String createLoginSession() {
        String token = UniqueID.getLoginToken();
        try {
            String sql = "SELECT token, date FROM LoginSessions WHERE email = ?";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, this.email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Check if the existing token is still valid
                Timestamp tokenDate = resultSet.getTimestamp("date");
                long tokenAge = System.currentTimeMillis() - tokenDate.getTime();
                long maxTokenAge = 30 * 24 * 60 * 60 * 1000; // 24 hours in milliseconds

                if (tokenAge < maxTokenAge) {
                    // The existing token is still valid, return it
                    return resultSet.getString("token");
                } else {
                    // The existing token has expired, update it
                    sql = "UPDATE LoginSessions SET token = ? WHERE email = ?";
                    statement = DBSetup.connect().prepareStatement(sql);
                    statement.setString(1, token);
                    statement.setString(2, this.email);
                    statement.executeUpdate();
                    return token;
                }
            } else {
                // No existing token, create a new one
                sql = "INSERT INTO LoginSessions (token, email) VALUES (?, ?)";
                statement = DBSetup.connect().prepareStatement(sql);
                statement.setString(1, token);
                statement.setString(2, this.email);
                statement.executeUpdate();
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String userTokenToID(String token) {
        String email = "";
        String sql = "SELECT email, date FROM LoginSessions WHERE token = ?";

        try (PreparedStatement statement = DBSetup.connect().prepareStatement(sql)) {
            statement.setString(1, token);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Timestamp tokenDate = resultSet.getTimestamp("date");
                    long tokenAge = System.currentTimeMillis() - tokenDate.getTime();
                    long maxTokenAge = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

                    if (tokenAge < maxTokenAge) {
                        email = resultSet.getString("email");
                    } else {
                        // The token has expired, delete it from the database
                        sql = "DELETE FROM LoginSessions WHERE token = ?";
                        try (PreparedStatement deleteStatement = DBSetup.connect().prepareStatement(sql)) {
                            deleteStatement.setString(1, token);
                            deleteStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getUserIdByEmail(email);
    }
    public static String getUserIdByEmail(String email) {
        String userId = ""; // Default value if no user is found
        String sql = "SELECT id FROM Users WHERE email = ?";
        if(email.isEmpty())
            return "17";
        try (PreparedStatement statement = DBSetup.connect().prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getString("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }


}
