package com.example.demo.api.User;

import com.example.demo.entity.User;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.exception.exceptions.InvalidCredentialsException;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountRequest user) {
        try {
            User newUser = authenticationService.register(user);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Trả về thông báo lỗi cụ thể
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration: " + e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
            return ResponseEntity.ok(authenticationResponse);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid username or password"); // Thông báo lỗi cụ thể
        } catch (LockedException e) {
            return ResponseEntity.badRequest().body("Account is locked");
        } catch (DisabledException e) {
            return ResponseEntity.badRequest().body("Account is disabled");
        } catch (AccountExpiredException e) {
            return ResponseEntity.badRequest().body("Account has expired");
        } catch (CredentialsExpiredException e) {
            return ResponseEntity.badRequest().body("Credentials have expired");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during login: " + e.getMessage());
        }
    }
}