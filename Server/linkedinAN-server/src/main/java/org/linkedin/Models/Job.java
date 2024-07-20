package org.linkedin.Models;

import org.linkedin.DB.DBSetup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Job {
    protected String jobTitle;
    protected String jobType;
    protected String nameOfCompany;
    protected String addressOfCompany;
    protected String working_type;
    protected int workingType;
    protected boolean stillWorking;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected String description;
    protected String skill1;
    protected String skill2;
    protected String skill3;
    protected String skill4;
    protected String skill5;
    public Job(String jobTitle, String nameOfCompany, String addressOfCompany, int workingType, LocalDateTime startTime,String description) {
        this.jobTitle = jobTitle;
        this.nameOfCompany = nameOfCompany;
        this.addressOfCompany = addressOfCompany;
        this.workingType = workingType;
        this.startTime = startTime;
        this.stillWorking = true;
        this.description = description;
    }
    public Job(String jobTitle, int jobType, String nameOfCompany, String addressOfCompany, int workingType,String description) {
        this.jobTitle = jobTitle;
        this.nameOfCompany = nameOfCompany;
        this.addressOfCompany = addressOfCompany;
        this.workingType = workingType;
        this.stillWorking = true;
        this.description = description;
    }

    public Job(String jobTitle, int jobType, String nameOfCompany, String addressOfCompany, int workingType, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.jobTitle = jobTitle;
        this.nameOfCompany = nameOfCompany;
        this.addressOfCompany = addressOfCompany;
        this.workingType = workingType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public void addSkill(String skill) {
        if(skill1==null){
            this.skill1=skill;
        } else if (skill2==null) {
            this.skill2=skill;

        } else if (skill3==null) {
            this.skill3=skill;

        }else if (skill4==null) {
            this.skill4=skill;
        }else if (skill5==null) {
            this.skill5=skill;
        }
    }
    public void changeSkill(int number,String skill) {
        switch (number){
            case 1: this.skill1=skill;return;
            case 2: this.skill2=skill;return;
            case 3: this.skill3=skill;return;
            case 4: this.skill4=skill;return;
            case 5: this.skill5=skill;return;
        }
    }

    public String getjobTitle() {
        return jobTitle;
    }

    public String getjobType() {
        return jobType;
    }


    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public String getAddressOfCompany() {
        return addressOfCompany;
    }

    public String getWorking_type() {
        return working_type;
    }

    public int getWorkingType() {
        return workingType;
    }

    public boolean isstillWorking() {
        return stillWorking;
    }

    public LocalDateTime getstartTime() {
        return startTime;
    }

    public LocalDateTime getendTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public String getSkill1() {
        return skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public String getSkill4() {
        return skill4;
    }

    public String getSkill5() {
        return skill5;
    }

    public void setjobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setjobType(String jobType) {
        this.jobType = jobType;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public void setAddressOfCompany(String addressOfCompany) {
        this.addressOfCompany = addressOfCompany;
    }

    public void setWorking_type(String working_type) {
        this.working_type = working_type;
    }

    public void setWorkingType(int workingType) {
        this.workingType = workingType;
    }

    public void setstillWorking(boolean stillWorking) {
        this.stillWorking = stillWorking;
    }

    public void setstartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setendTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void insertDB() {
        try{
            Connection connection = DBSetup.connect();
            String sql = "INSERT INTO Job (jobTitle,jobType,nameOfCompany,addressOfCompany,working_type,startTime,endTime,description ,skill1, skill2, skill3, skill4, skill5) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,jobTitle );
            pstmt.setString(2, jobType);
            pstmt.setString(3, nameOfCompany);
            pstmt.setString(4, addressOfCompany);
            pstmt.setString(5, working_type);
            pstmt.setObject(6, startTime);
            pstmt.setObject(7, endTime);
            pstmt.setString(8, description);
            pstmt.setString(9, skill1);
            pstmt.setString(10, skill2);
            pstmt.setString(11, skill3);
            pstmt.setString(12, skill4);
            pstmt.setString(13, skill5);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}