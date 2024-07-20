package com.example.test.Models;

import java.sql.Date;

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

        public String getId() {
                return id;
        }

        public String getNameOfEducationCenter() {
                return nameOfEducationCenter;
        }

        public String getFieldOfStudy() {
                return fieldOfStudy;
        }

        public CharSequence getStartDateOfEducation() {
                return (CharSequence) startDateOfEducation;
        }

        public CharSequence getEndDateOfEducation() {
                return (CharSequence) endDateOfEducation;
        }

        public float getGradeOfEducation() {
                return gradeOfEducation;
        }

        public String getDescriptionOfEducation() {
                return descriptionOfEducation;
        }

        public String getOtherDescriptionOf() {
                return otherDescriptionOf;
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

        public String getUserID() {
                return userID;
        }

        public String getOtherUser() {
                return otherUser;
        }
}
