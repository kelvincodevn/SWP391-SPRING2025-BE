package be.mentalhealth.springboot_backend.entity;


import be.mentalhealth.springboot_backend.enums.RoleEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import be.mentalhealth.springboot_backend.DTO.UserDTO;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

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


    private LocalDateTime dob;

    private String phone;


    private LocalDateTime createdDate;


    private Boolean status;


    private String gender;


    private String avatar;

    @Enumerated(value = EnumType.STRING)
    public RoleEnum roleEnum;

    public boolean isDeleted = false;

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Adjust if using roles
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username; // Fix: return userName instead of null
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust logic as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust logic as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust logic as needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Ensure status is checked
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

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
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

    // Constructor with all fields


    public User(Long userID, String username, String password, String fullName, String email, LocalDateTime dob, String phone, LocalDateTime createdDate, Boolean status, String gender, String avatar, RoleEnum roleEnum) {
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

    public User() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void updateUser(UserDTO request) {
    }
}
