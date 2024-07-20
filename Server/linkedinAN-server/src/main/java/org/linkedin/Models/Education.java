package org.linkedin.Models;

import org.linkedin.DB.DBSetup;
import org.linkedin.DB.UniqueID;

import java.sql.*;
import java.util.Objects;

public class Education {
    private String id;
    private String nameOfEducationCenter;
    private String fieldOfStudy;
    private Date startDateOfEducation;
    private Date endDateOfEducation;
    private float gradeOfEducation;
    private String descriptionOfEducation;
    private String otherDescriptionOf;
    private String skill1;
    private String skill2;
    private String skill3;
    private String skill4;
    private String skill5;
    private String userID;
    private String otherUser;
    public Education() {
        this.id = String.valueOf(UniqueID.generateUniqueID());
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setNameOfEducationCenter(String nameOfEducationCenter) {
        this.nameOfEducationCenter = nameOfEducationCenter;
    }

    public void setFieldOfStudy(String feieldOfStudy) {
        this.fieldOfStudy = feieldOfStudy;
    }

    public void setStartDateOfEducation(Date startDateOfEducation) {
        this.startDateOfEducation = startDateOfEducation;
    }

    public void setEndDateOfEducation(Date endDateOfEducation) {
        this.endDateOfEducation = endDateOfEducation;
    }

    public void setDescriptionOfEducation(String descriptionOfEducation) {
        this.descriptionOfEducation = descriptionOfEducation;
    }

    public void setOtherDescriptionOf(String otherDescriptionOf) {
        this.otherDescriptionOf = otherDescriptionOf;
    }

    public String getSkill5() {
        return skill5;
    }

    public String getSkill4() {
        return skill4;
    }

    public String getSkill3() {
        return skill3;
    }

    public String getSkill2() {
        return skill2;
    }

    public String getSkill1() {
        return skill1;
    }

    public String getOtherDescriptionOf() {
        return otherDescriptionOf;
    }

    public String getDescriptionOfEducation() {
        return descriptionOfEducation;
    }

    public double getGradeOfEducation() {
        return gradeOfEducation;
    }

    public Date getEndDateOfEducation() {
        return endDateOfEducation;
    }

    public Date getStartDateOfEducation() {
        return startDateOfEducation;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getNameOfEducationCenter() {
        return nameOfEducationCenter;
    }
    public void insertDB() {
        try{
            Connection connection = DBSetup.connect();
            String sql = "INSERT INTO Education (id) VALUES (?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGradeOfEducation(float gradeOfEducation) {
        this.gradeOfEducation = gradeOfEducation;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

    public void setSkill4(String skill4) {
        this.skill4 = skill4;
    }

    public void setSkill5(String skill5) {
        this.skill5 = skill5;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public boolean updateDB() {
        String educationID = User.getEducationID(userID);
        if (educationID == null || educationID.isEmpty())
            return false;
        try {
            String sql = "UPDATE Education " +
                    "SET nameOfEducationCenter = COALESCE(?, nameOfEducationCenter), " +
                    "    startDateOfEducation = COALESCE(?, startDateOfEducation), " +
                    "    endDateOfEducation = COALESCE(?, endDateOfEducation), " +
                    "    gradeOfEducation = COALESCE(?, gradeOfEducation), " +
                    "    descriptionOfEducation = COALESCE(?, descriptionOfEducation), " +
                    "    otherDescriptionOf = COALESCE(?, otherDescriptionOf), " +
                    "    skill1 = COALESCE(?, skill1), " +
                    "    skill2 = COALESCE(?, skill2), " +
                    "    skill3 = COALESCE(?, skill3), " +
                    "    skill4 = COALESCE(?, skill4) " +
                    "WHERE id = ?";

            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, !Objects.equals(this.nameOfEducationCenter, "") ? this.nameOfEducationCenter : null);
            statement.setObject(2, this.startDateOfEducation != null ? this.startDateOfEducation : null);
            statement.setObject(3, this.endDateOfEducation != null ? this.endDateOfEducation : null);
            statement.setFloat(4, this.gradeOfEducation);
            statement.setString(5, !Objects.equals(this.descriptionOfEducation, "") ? this.descriptionOfEducation : null);
            statement.setString(6, !Objects.equals(this.otherDescriptionOf, "") ? this.otherDescriptionOf : null);
            statement.setString(7, !Objects.equals(this.skill1, "") ? this.skill1 : null);
            statement.setString(8, !Objects.equals(this.skill2, "") ? this.skill2 : null);
            statement.setString(9, !Objects.equals(this.skill3, "") ? this.skill3 : null);
            statement.setString(10, !Objects.equals(this.skill4, "") ? this.skill4 : null);
            statement.setString(11, educationID);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void getEducationData() {
        String educationID = "";
        if(!Objects.equals(this.otherUser, "") && this.otherUser != null)
            educationID = User.getEducationID(otherUser);
        else
            educationID = User.getEducationID(this.userID);
        if (educationID == null || educationID.isEmpty()) {
            return;
        }
        try {
            String sql = "SELECT nameOfEducationCenter, startDateOfEducation, endDateOfEducation, gradeOfEducation, descriptionOfEducation, otherDescriptionOf, skill1, skill2, skill3, skill4 " +
                    "FROM Education " +
                    "WHERE id = ?";

            PreparedStatement statement = DBSetup.connect().prepareStatement(sql);
            statement.setString(1, educationID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.setNameOfEducationCenter(resultSet.getString("nameOfEducationCenter"));
                this.setStartDateOfEducation(resultSet.getDate("startDateOfEducation"));
                this.setEndDateOfEducation(resultSet.getDate("endDateOfEducation"));
                this.setGradeOfEducation(resultSet.getFloat("gradeOfEducation"));
                this.setDescriptionOfEducation(resultSet.getString("descriptionOfEducation"));
                this.setOtherDescriptionOf(resultSet.getString("otherDescriptionOf"));
                this.setSkill1(resultSet.getString("skill1"));
                this.setSkill2(resultSet.getString("skill2"));
                this.setSkill3(resultSet.getString("skill3"));
                this.setSkill4(resultSet.getString("skill4"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }


}