package com.example.demo.api.Manager;

import com.example.demo.entity.Tests;
import com.example.demo.entity.User;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.enums.RoleEnum;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/manager")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('MANAGER')") // Chỉ MANAGER amới có quyền truy cập
public class ManagerAPI {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ManagerService managerService;

    @GetMapping
    public ResponseEntity getAllUser(){
        List<User> users = managerService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable long id){
        User user = managerService.deleteUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody AccountRequest user) {
        return managerService.updateUser(id, user);
    }

    @PostMapping("/register-psychologist")
    public ResponseEntity<User> registerPsychologist(@RequestBody AccountRequest accountRequest) {
        accountRequest.setRoleEnum(RoleEnum.PSYCHOLOGIST);
        User psychologist = authenticationService.register(accountRequest);
        return ResponseEntity.ok(psychologist);
    }


}
