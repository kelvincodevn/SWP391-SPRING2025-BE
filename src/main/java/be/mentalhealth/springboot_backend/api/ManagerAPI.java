package be.mentalhealth.springboot_backend.api;


import be.mentalhealth.springboot_backend.DTO.UserDetailDTO;
import be.mentalhealth.springboot_backend.DTO.UserViewDTO;
import be.mentalhealth.springboot_backend.service.ManagerService;
import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/Manager/api/User")
@SecurityRequirement(name = "api")
public class ManagerAPI {

    @Autowired
    ManagerService managerService;

    @GetMapping
    public ResponseEntity getAllUser(){
        List<UserViewDTO> users = managerService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public UserDetailDTO getUserById(@PathVariable Long userId) {
        return managerService.getUserById(userId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable long id){
        User user = managerService.deleteUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody AccountRequest user) {
        return managerService.updateUser(id, user);
    }




}
