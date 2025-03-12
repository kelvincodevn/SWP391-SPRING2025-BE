package be.mentalhealth.springboot_backend.api;


import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.entity.request.AuthenticationRequest;
import be.mentalhealth.springboot_backend.entity.response.AuthenticationResponse;
import be.mentalhealth.springboot_backend.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody AccountRequest user){
        User newUser = authenticationService.register(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
    AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
    return ResponseEntity.ok(authenticationResponse);
    }
}
