package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.service.AuthenticationService;
import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.entity.request.AuthenticationRequest;
import be.mentalhealth.springboot_backend.entity.response.AuthenticationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody AccountRequest user){
        User newUser = authenticationService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(authenticationResponse);
    }


}
