package org.linkedin.Models;

import org.linkedin.DB.DBSetup;
import org.linkedin.DB.UniqueID;

import java.sql.*;

public class Like {
    private String IDLike;
    private String IDUser;
    private String IDPost;

    public Like( String IDUser, String IDPost) {
        this.IDLike = String.valueOf(UniqueID.generateUniqueID());
        this.IDUser = IDUser;
        this.IDPost = IDPost;
    }

    public void setIDLike() {
        this.IDLike = String.valueOf(UniqueID.generateUniqueID());
    }

    public String getIDLike() {
        return IDLike;
    }

    public String getIDUser() {
        return IDUser;
    }

    public String getIDPost() {
        return IDPost;
    }
    public void insertDB() {
        try {
            Connection connection = DBSetup.connect();
            String sql = "INSERT INTO Like (IDLike, IDUser, IDPost) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, IDLike);
            pstmt.setString(2, IDUser);
            pstmt.setString(3, IDPost);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isLiked(){
        try {
            String checkSql = "SELECT COUNT(*) FROM Likes WHERE IDUser = ? AND IDPost = ?";
            PreparedStatement checkStmt = DBSetup.connect().prepareStatement(checkSql);
            checkStmt.setString(1, IDUser);
            checkStmt.setString(2, IDPost);
            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                resultSet.close();
                checkStmt.close();
                return count > 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Long getUserID() {
        return Long.valueOf(this.IDUser);
    }
    public void doLikeAction() {
        if(!isLiked()){
            this.IDLike = String.valueOf(UniqueID.generateUniqueID());
            insertDB();
        }else{
            deleteLike();
        }
    }
    public void deleteLike(){
        try {
            String deleteRowSql = "DELETE FROM Likes WHERE IDUser = ? AND IDPost = ?";
            PreparedStatement deleteRowStmt = DBSetup.connect().prepareStatement(deleteRowSql);
            deleteRowStmt.setString(1, IDUser);
            deleteRowStmt.setString(2, IDPost);
            deleteRowStmt.executeUpdate();
//            int rowsAffected =
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static int numberOfLikesPost(String IDPost){
        try (PreparedStatement stmt = DBSetup.connect().prepareStatement("SELECT COUNT(*) FROM Likes WHERE IDPost = ?")) {
            stmt.setString(1, IDPost);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int rowCount = rs.getInt(1);
                    return rowCount;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }
}
