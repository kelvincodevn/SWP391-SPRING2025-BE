package com.example.demo.api;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "api")
public class UserAPI {
    @Autowired
    private UserService userService;

    // API xem profile (chỉ user đã đăng nhập mới truy cập được)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile() {
        return ResponseEntity.ok(userService.getUserProfile());
    }

    // API cập nhật profile
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UserProfileDTO updatedProfile) {
        return ResponseEntity.ok(userService.updateUserProfile(updatedProfile));
    }
}
