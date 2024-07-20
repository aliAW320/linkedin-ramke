package org.linkedin.Models;

import org.linkedin.DB.DBSetup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JobChance extends Job{
// this class is created just to make to maintenance easier
private String userId;

    public JobChance(String job_title, int jobType, String nameOfCompany, String addressOfCompany, int workingType, String description, String userId) {
        super(job_title, jobType, nameOfCompany, addressOfCompany, workingType, description);
        this.userId= userId;
    }
    @Override
    public void insertDB() {
            try {
                Connection connection = DBSetup.connect();
                String sql = "INSERT INTO Job (job_title, job_type, nameOfCompany, addressOfCompany, working_type, description, skill1, skill2, skill3, skill4, skill5, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, jobTitle);
                pstmt.setString(2, jobType);
                pstmt.setString(3, nameOfCompany);
                pstmt.setString(4, addressOfCompany);
                pstmt.setInt(5, workingType);
                pstmt.setString(6, description);
                pstmt.setString(7, skill1);
                pstmt.setString(8, skill2);
                pstmt.setString(9, skill3);
                pstmt.setString(10, skill4);
                pstmt.setString(11, skill5);
                pstmt.setString(12, userId);
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
