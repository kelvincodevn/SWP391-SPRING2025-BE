package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.entity.Account;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    // /api/register
    @PostMapping("register")
    //đừng bao giờ đẻ entities vào cái @RequestBody
    public ResponseEntity register(@Valid @RequestBody AccountRequest account) {
        Account newAccount = authenticationService.register(account);
        return ResponseEntity.ok(newAccount);
    }
}
