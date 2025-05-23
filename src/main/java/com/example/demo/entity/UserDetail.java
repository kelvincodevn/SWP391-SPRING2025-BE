package com.example.demo.entity;

import com.example.demo.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_details")
@Builder
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userDetailId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    private String major;
    private String workplace;
    private String degree;
    private Double fee;
    private Integer experience; // Thêm field experience (số năm kinh nghiệm)

    @PrePersist
    @PreUpdate
    public void validateRole() {
        if (user.getRoleEnum() != RoleEnum.PSYCHOLOGIST) {
            throw new IllegalStateException("UserDetail is only for Psychologists");
        }
    }

    public UserDetail() {
    }

    public UserDetail(Integer userDetailId, User user, String major, String workplace, String degree, Double fee, Integer experience) {
        this.userDetailId = userDetailId;
        this.user = user;
        this.major = major;
        this.workplace = workplace;
        this.degree = degree;
        this.fee = fee;
        this.experience = experience;
    }

    public Integer getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}