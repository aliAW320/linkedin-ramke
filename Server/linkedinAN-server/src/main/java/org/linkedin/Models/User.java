package org.linkedin.Models;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.linkedin.DB.DBSetup;
import org.linkedin.DB.UniqueID;
import org.linkedin.utils.Response;
import org.linkedin.utils.Validation;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class User {
    private String id;
    private String email;
    private String name;
    private String familyName;
    private String password;
    private String additionalName;
    private String profilePhoto;
    private String backgroundPhoto;
    private String title;
    private String job;
    private String country;
    private String city;
    private String profession;
    private String jobPosition;
    private String experience;
    private String license;
    private Education education;
    private CallInfo callInfo;

    public User(String id) {
        this.id = id;
    }

    public User(String email, String password, String name, String familyName, String additionalName, String profilePhoto, String backgroundPhoto, String title, String job, String country, String city, String profession, String jobPosition, String experience, String license, Education education, CallInfo callInfo) {
        this.id = String.valueOf(UniqueID.generateUniqueID());
        this.email = email;
        this.name = name;
        this.familyName = familyName;
        this.password = password;
        this.additionalName = additionalName;
        this.profilePhoto = profilePhoto;
        this.backgroundPhoto = backgroundPhoto;
        this.title = title;
        this.job = job;
        this.country = country;
        this.city = city;
        this.profession = profession;
        this.jobPosition = jobPosition;
        this.experience = experience;
        this.license = license;
        this.education = education;
        this.callInfo = callInfo;
    }

    public User() {

    }

    public void insertDB(){
        if(education == null){
            education = new Education();
        }
        if(callInfo == null){
            callInfo = new CallInfo();
        }
        education.insertDB();
        callInfo.insertDB();
        try {
            Connection connection = DBSetup.connect();
            String sql = "INSERT INTO Users (id,email,password, name, familyName,education,callINfo,country,city) VALUES (?,?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, Long.parseLong(id));
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, name);
            pstmt.setString(5, familyName);
            pstmt.setString(6, education.getId());
            pstmt.setString(7, callInfo.getId());
            pstmt.setString(8, country);
            pstmt.setString(9, city);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return this.id;
    }
    public void setId() {
        this.id = String.valueOf(UniqueID.generateUniqueID());
    }
    public Response userCreateValidation(){
        if(!Validation.emailIsValid(this.email)){
            return new Response(false,"email is invalid!",400);
        }
        Connection connection = DBSetup.connect();
        try (PreparedStatement userExist = connection.prepareStatement("SELECT COUNT(*) FROM Users WHERE email = ?")) {
            userExist.setString(1, email);
            try (ResultSet rs = userExist.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        return new Response(false, "email is exist!", 200);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        if(!Validation.passIsValid(this.password)){
            return new Response(false,"password is too weak! use at least eight character an at least one letter",200);
        }
        return new Response(true,"valid!",200);
    }
    public Response userLoginValidation() {
        Connection connection = DBSetup.connect();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    System.out.println("Stored password: " + storedPassword);
                    System.out.println("Entered password: " + password);
                    if (!Objects.equals(storedPassword, password)) {
                        return new Response(false, "Email or password is not correct!", 400);
                    } else {
                        // Generate the JWT token
                        Map<String, Object> claims = new HashMap<>();
                        claims.put("sub", email);
                        claims.put("iat", new Date(System.currentTimeMillis()));
                        claims.put("exp", new Date(System.currentTimeMillis() + 86400000)); // 1 day expiration

                        String token = Jwts.builder()
                                .setClaims(claims)
                                .signWith(SignatureAlgorithm.HS256, "UiWLQvbN4uQrBHcfAGKqTizE5o7MG+jhXhkNy/knuj4=".getBytes("UTF-8"))
                                .compact();

                        System.out.println("Generated token: " + token);
                        return new Response(true, "Login successful", 200, token);
                    }
                } else {
                    return new Response(false, "Email or password is not correct!", 400);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(false, "An error occurred while validating the user", 500);
        }
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    //TODO replace with session & login
    public static boolean isUserExist(Long id){
        PreparedStatement pstmt = null;
        try {
            Connection connection = DBSetup.connect();
            String sql = "select * from Users where id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            return pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean authenticateUser(String email, String password) {
        try {
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void setId(String id) {
        this.id = id;
    }
    public void updateUser(){
        try {
            String sql = "UPDATE Users " +
                    "SET name = COALESCE(?, name), " +
                    "    familyName = COALESCE(?, familyName), " +
                    "    country = COALESCE(?, country), " +
                    "    city = COALESCE(?, city), " +
                    "    profession = COALESCE(?, profession), " +
                    "    job = COALESCE(?, job), " +
                    "    jobPosition = COALESCE(?, jobPosition) " +
                    "WHERE id = ?";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, !Objects.equals(this.name, "") ? this.name : null);
            statement.setString(2, !Objects.equals(this.familyName, "")  ? this.familyName : null);
            statement.setString(3, !Objects.equals(this.country, "") ? this.country : null);
            statement.setString(4, !Objects.equals(this.city, "") ? this.city : null);
            statement.setString(5, !Objects.equals(this.profession, "") ? this.profession : null);
            statement.setString(6, !Objects.equals(this.job, "") ? this.job : null);
            statement.setString(7, !Objects.equals(this.jobPosition, "") ? this.jobPosition : null);
            statement.setString(8, this.id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static String getEducationID(String id){
        PreparedStatement pstmt = null;
        try {
            Connection connection = DBSetup.connect();
            String sql = "select * from Users where id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                   return rs.getString("education");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void getUserData() {
        try {
            String sql = "SELECT email,name, familyName, country, city, profession, job, jobPosition " +
                    "FROM Users " +
                    "WHERE id = ?";
            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.name = resultSet.getString("name");
                this.familyName = resultSet.getString("familyName");
                this.country = resultSet.getString("country");
                this.city = resultSet.getString("city");
                this.profession = resultSet.getString("profession");
                this.job = resultSet.getString("job");
                this.jobPosition = resultSet.getString("jobPosition");
                this.email = resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
