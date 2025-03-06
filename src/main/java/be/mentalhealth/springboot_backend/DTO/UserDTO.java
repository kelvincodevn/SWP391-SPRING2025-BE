package be.mentalhealth.springboot_backend.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private LocalDateTime dob;
    private String phone;
    private String gender;
    private String avatar;


    // Getters và Setters


    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
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

    public UserDTO() {
    }

    public UserDTO(String username, String password, String fullName, String email, LocalDateTime dob, String phone, String gender, String avatar) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.gender = gender;
        this.avatar = avatar;
    }
}