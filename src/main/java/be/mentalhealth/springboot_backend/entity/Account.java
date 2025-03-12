package be.mentalhealth.springboot_backend.entity;

import be.mentalhealth.springboot_backend.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;


import java.util.ArrayList;
import java.util.Collection;

@Entity

public class Account implements UserDetails {

    //xác định khóa chính cho table (generate tự động chứ ko có tự nhập)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate tự động id
    public long id;

    public String fullName;
    public String email;
    public String username;
    public String password; //ctrl + D

    @Enumerated(value = EnumType.STRING)
    public be.mentalhealth.springboot_backend.enums.RoleEnum roleEnum;

    public be.mentalhealth.springboot_backend.enums.RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(be.mentalhealth.springboot_backend.enums.RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    @JsonSubTypes({
            @Type(value = SimpleGrantedAuthority.class, name = "SimpleGrantedAuthority")
    })

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.roleEnum.toString()));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account(long id, String fullName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public Account() {
    }
}