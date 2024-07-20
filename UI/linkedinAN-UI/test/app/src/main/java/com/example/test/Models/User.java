package com.example.test.Models;

import android.widget.ImageView;

public class User
{
    private String ID;
    private String name;
    private String email;
    private ImageView profileImage;
    private String id;
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

    public User(String ID, ImageView profileImage, String email, String name) {
        this.ID = ID;
        if (profileImage != null) {
        this.profileImage = profileImage;}
        else {

        }
        this.email = email;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }

    public String getId() {
        return id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPassword() {
        return password;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getBackgroundPhoto() {
        return backgroundPhoto;
    }

    public String getTitle() {
        return title;
    }

    public String getJob() {
        return job;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getProfession() {
        return profession;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public String getExperience() {
        return experience;
    }

    public String getLicense() {
        return license;
    }

    public Education getEducation() {
        return education;
    }

    public CallInfo getCallInfo() {
        return callInfo;
    }
}
