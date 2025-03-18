package com.example.demo.api.Manager;

import com.example.demo.entity.User;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.service.ManagerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/manager")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('MANAGER')")
public class ManagerAPI {
    private final ManagerService managerService;

    @Autowired
    public ManagerAPI(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(managerService.getAllUser());
    }

    @PostMapping("/create-psychologist")
    public ResponseEntity<User> createPsychologist(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(managerService.createPsychologist(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updatePsychologist(@PathVariable Long id, @RequestBody AccountRequest request) {
        return ResponseEntity.ok(managerService.updatePsychologist(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.deleteUser(id));
    }
}
