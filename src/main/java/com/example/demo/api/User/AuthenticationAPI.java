package com.example.demo.api.User;

import com.example.demo.entity.User;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
//@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;

//    @PostMapping("register")
//    public ResponseEntity register(@Valid @RequestBody AccountRequest user){
//        User newUser = authenticationService.register(user);
//        return ResponseEntity.ok(newUser);
//        //HttpStatus.CREATED (200)
//    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountRequest user) {
        try {
            User newUser = authenticationService.register(user);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration: " + e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
    AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
    return ResponseEntity.ok(authenticationResponse);
    }
}
