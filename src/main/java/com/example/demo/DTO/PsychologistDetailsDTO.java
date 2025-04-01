package com.example.demo.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PsychologistDetailsDTO {
    private Long userID;
    private String username;
    private String fullName;
    private String email;
    private LocalDate dob;
    private String phone;
    private LocalDateTime createdDate;
    private String gender;
    private String major;
    private String degree;
    private String workplace;
    private Double fee; // Thêm field fee

    public PsychologistDetailsDTO() {
    }

    public PsychologistDetailsDTO(Long userID, String username, String fullName, String email, LocalDate dob, String phone, LocalDateTime createdDate, String gender, String major, String degree, String workplace, Double fee) {
        this.userID = userID;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.createdDate = createdDate;
        this.gender = gender;
        this.major = major;
        this.degree = degree;
        this.workplace = workplace;
        this.fee = fee;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
