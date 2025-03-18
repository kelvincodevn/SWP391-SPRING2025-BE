package com.example.demo.DTO;

import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserProfileDTO {



    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;


    private LocalDate dob;

    private String phone;


    private LocalDateTime createdDate;


    private String gender;


    private String avatar;

    // Constructor

    public UserProfileDTO() {
    }

    public UserProfileDTO(String fullName, String email, LocalDate dob, String phone, LocalDateTime createdDate, String gender, String avatar) {
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.createdDate = createdDate;
        this.gender = gender;
        this.avatar = avatar;
    }

    // Getter & Setter


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
