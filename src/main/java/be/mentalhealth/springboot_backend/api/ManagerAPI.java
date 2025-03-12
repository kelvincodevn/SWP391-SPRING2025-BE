package be.mentalhealth.springboot_backend.api;


import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.service.ManagerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/Manager")
@SecurityRequirement(name = "api")
public class ManagerAPI {

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




}
