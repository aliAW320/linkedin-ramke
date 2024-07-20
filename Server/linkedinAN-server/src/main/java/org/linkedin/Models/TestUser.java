package org.linkedin.Models;

import org.linkedin.DB.UniqueID;

public class TestUser {
    private long id;
    private String email;
    private String name;
    private String familyName;
    private String additionalName;
    private String profilePhoto;
    private String backgroundPhoto;
    private String title;
    private String job;
    private String country;
    private String city;

    public TestUser(String email, String name, String familyName, String additionalName, String profilePhoto, String backgroundPhoto, String title, String job, String country, String city) {
        this.id = UniqueID.generateUniqueID();
        this.email = email;
        this.name = name;
        this.familyName = familyName;
        this.additionalName = additionalName;
        this.profilePhoto = profilePhoto;
        this.backgroundPhoto = backgroundPhoto;
        this.title = title;
        this.job = job;
        this.country = country;
        this.city = city;
    }
}
