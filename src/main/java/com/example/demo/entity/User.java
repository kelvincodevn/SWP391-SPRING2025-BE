package com.example.demo.entity;

import com.example.demo.DTO.UserDTO;
import com.example.demo.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    private LocalDate dob;

    private String phone;

    private LocalDateTime createdDate;

    private Boolean status;

    private String gender;

    private String avatar;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum roleEnum;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDetail userDetail;

    // Mối quan hệ với ParentStudent (phụ huynh quản lý học sinh)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ngắt vòng lặp
    private List<ParentStudent> managedStudents = new ArrayList<>();

    // Mối quan hệ với ParentStudent (học sinh được quản lý bởi phụ huynh)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ngắt vòng lặp
    private List<ParentStudent> managedByParents = new ArrayList<>();

    @Transient
    private List<GrantedAuthority> authorities;

    public boolean isDeleted = false;

    // Getters và Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<ParentStudent> getManagedStudents() {
        return managedStudents;
    }

    public void setManagedStudents(List<ParentStudent> managedStudents) {
        this.managedStudents = managedStudents;
    }

    public List<ParentStudent> getManagedByParents() {
        return managedByParents;
    }

    public void setManagedByParents(List<ParentStudent> managedByParents) {
        this.managedByParents = managedByParents;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(roleEnum.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.status = true;
    }

    public User() {
    }

    public User(Long userID, String username, String password, String fullName, String email, LocalDate dob, String phone, LocalDateTime createdDate, Boolean status, String gender, String avatar, RoleEnum roleEnum) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.createdDate = createdDate;
        this.status = status;
        this.gender = gender;
        this.avatar = avatar;
        this.roleEnum = roleEnum;
    }

    public void updateUser(UserDTO request) {
    }
}