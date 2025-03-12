package com.example.demo.service;

import com.example.demo.Repository.AuthenticationRepository;

import com.example.demo.entity.User;
import com.example.demo.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    private final String SECRET_KEY = "HT4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";

    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user){
        String token = Jwts.builder()
                .subject(user.getUserID()+"")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("role", user.getRoleEnum().name()) // Thêm vai trò vào claims
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    public User getAccountByToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String idString = claims.getSubject();
        long UserID = Long.parseLong(idString);

        User user = authenticationRepository.findByUserID(UserID);
        if (user != null) {
            String roleName = claims.get("role", String.class);
            user.setRoleEnum(RoleEnum.valueOf(roleName)); // Set role cho user

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRoleEnum().name()));
            user.setAuthorities(authorities); // Thêm authorities vào User
        }
        return user;
    }

}
