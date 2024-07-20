package org.linkedin.DB;

import java.sql.SQLException;
import java.sql.Statement;
public class CreateTables {
    public static void createUserDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                    "    id BIGINT PRIMARY KEY," +
                    "    name VARCHAR(50)," +
                    "    familyName VARCHAR(50)," +
                    "    email VARCHAR(50)," +
                    "    password VARCHAR(50)," +
                    "    additionalName VARCHAR(50)," +
                    "    profilePhoto VARCHAR(50)," +
                    "    backgroundPhoto VARCHAR(50)," +
                    "    title VARCHAR(50)," +
                    "    job VARCHAR(50)," +
                    "    country VARCHAR(50)," +
                    "    city VARCHAR(50)," +
                    "    profession VARCHAR(50)," +
                    "    jobPosition VARCHAR(50)," +
                    "    experience VARCHAR(50)," +
                    "    license VARCHAR(50)," +
                    "    education BIGINT," +
                    "    callInfo BIGINT," +
                    "    FOREIGN KEY (education) REFERENCES Education(id)," +
                    "    FOREIGN KEY (callInfo) REFERENCES CallInfo(id)" +
                    ")";
            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void callInfoDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS CallInfo (" +
                    "id BIGINT PRIMARY KEY," +
                    "phoneNumber VARCHAR(50)," +
                    "email VARCHAR(50)" +
                    ")";

            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void educationDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Education (" +
                    "id BIGINT PRIMARY KEY," +
                    "nameOfEducationCenter VARCHAR(40)," +
                    "startDateOfEducation TIMESTAMP," +
                    "endDateOfEducation TIMESTAMP," +
                    "gradeOfEducation float," +
                    "descriptionOfEducation VARCHAR(500)," +
                    "otherDescriptionOf VARCHAR(1000)," +
                    "skill1 VARCHAR(40)," +
                    "skill2 VARCHAR(40)," +
                    "skill3 VARCHAR(40)," +
                    "skill4 VARCHAR(40)" +
                    ")";
            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void likeDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Likes (" +
                    "    id BIGINT PRIMARY KEY," +
                    "    IDUser BIGINT NOT NULL," +
                    "    IDPost BIGINT NOT NULL," +
                    "    FOREIGN KEY (IDUser) REFERENCES Users(id)," +
                    "    FOREIGN KEY (IDPost) REFERENCES TextPost(id)" +
                    ")";

            createStatement.execute(sql);
            createStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public static void jobDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Job (" +
                    "    job_title VARCHAR(40) NOT NULL," +
                   // "    job_type VARCHAR(255) NOT NULL," +
                    "    jobType INT NOT NULL," +
                    "    nameOfCompany VARCHAR(40) NOT NULL," +
                    "    addressOfCompany VARCHAR(40) NOT NULL," +
                   // "    working_type VARCHAR(255) NOT NULL," +
                    "    workingType INT NOT NULL," +
                    "    still_working BOOLEAN NOT NULL," +
                    "    start_time TIMESTAMP," +
                    "    end_time TIMESTAMP," +
                    "    description VARCHAR(1000)," +
                    "    skill1 VARCHAR(40)," +
                    "    skill2 VARCHAR(40)," +
                    "    skill3 VARCHAR(40)," +
                    "    skill4 VARCHAR(40)," +
                    "    skill5 VARCHAR(40)," +
                    "    PRIMARY KEY (job_title, job_type, nameOfCompany)" +
                    ")";

            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void commentDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Comment (" +
                    "    id BIGINT PRIMARY KEY," +
                    "    comment VARCHAR(1250)," +
                    "    userId BIGINT NOT NULL," +
                    "    date TIMESTAMP NOT NULL," +
                    "    FOREIGN KEY (userId) REFERENCES Users(id)" +
                    ")";
            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void TextPostDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS TextPost (" +
                    "    id BIGINT PRIMARY KEY," +
                    "    userID BIGINT," +
                    "    text VARCHAR(3000) NOT NULL," +
                    "    likes INT DEFAULT 0," +
                    "    FOREIGN KEY (userID) REFERENCES Users(id)" +
                    ")";
            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void loginSessions(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS LoginSessions (" +
                    "    token VARCHAR(60) PRIMARY KEY," +
                    "    email VARCHAR(50)," +
                    "    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void jobChanceDB(){
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Job (" +
                    "    jobTitle VARCHAR(40) NOT NULL," +
                    "    jobType INT NOT NULL," +
                    "    nameOfCompany VARCHAR(40) NOT NULL," +
                    "    addressOfCompany VARCHAR(40) NOT NULL," +
                    "    workingType INT NOT NULL," +
                    "    description  VARCHAR(1100)," +
                    "    skill1 VARCHAR(40)," +
                    "    skill2 VARCHAR(40)," +
                    "    skill3 VARCHAR(40)," +
                    "    skill4 VARCHAR(40)," +
                    "    skill5 VARCHAR(40)," +
                    "    userId VARCHAR(255) NOT NULL," +
                    "    FOREIGN KEY (userId) REFERENCES Users(id)" +
                    ")";
            createStatement.execute(sql);
            createStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void followsDB() {
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Follows (" +
                    "    id BIGINT PRIMARY KEY," +
                    "    follower BIGINT," +
                    "    following BIGINT," +
                    "    FOREIGN KEY (follower) REFERENCES Users(id)," +
                    "    FOREIGN KEY (following) REFERENCES Users(id)" +
                    ")";
            createStatement.execute(sql);
            createStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void chatDB() {
        try {
            Statement createStatement = DBSetup.connect().createStatement();
            String sql =    "CREATE TABLE IF NOT EXISTS Chat ("+
                    "id BIGINT PRIMARY KEY,"+
                    "senderId BIGINT NOT NULL,"+
                    "receiverId BIGINT NOT NULL,"+
                    "message VARCHAR(1900) NOT NULL,"+
                    "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
                    "FOREIGN KEY (senderId) REFERENCES Users(id),"+
                    "FOREIGN KEY (receiverId) REFERENCES Users(id)"+
            ")";
            createStatement.execute(sql);
            createStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
