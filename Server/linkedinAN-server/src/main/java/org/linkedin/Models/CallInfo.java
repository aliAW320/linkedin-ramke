package org.linkedin.Models;

import org.linkedin.DB.DBSetup;
import org.linkedin.DB.UniqueID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CallInfo {
    private String id;
    private String phoneNumber;
    private String email;
    public CallInfo() {
        this.id = String.valueOf(UniqueID.generateUniqueID());
    }
    public CallInfo(String phoneNumber, String email) {
        this.id = String.valueOf(UniqueID.generateUniqueID());
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void insertDB(){
        try {
            Connection connection = DBSetup.connect();
            String sql = "INSERT INTO CallInfo (id) VALUES (?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, Long.parseLong(id));
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
