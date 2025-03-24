package be.mentalhealth.springboot_backend.entity.response;

import be.mentalhealth.springboot_backend.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class AuthenticationResponse {

    public long userID;
    public String fullName;

    public String username;
    public String email;


    @Enumerated(value = EnumType.STRING)
    public RoleEnum roleEnum;
    public String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(long userID, String fullName, String username, String email, RoleEnum roleEnum, String token) {
        this.userID = userID;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.roleEnum = roleEnum;
        this.token = token;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


