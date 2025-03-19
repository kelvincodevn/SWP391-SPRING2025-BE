package com.example.demo.api.User;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "api")
public class UserAPI {
    @Autowired
    private UserService userService;

    // API xem profile (chỉ user đã đăng nhập mới truy cập được)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile() {
        try {
            UserProfileDTO profile = userService.getUserProfile();
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            // Log lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // API cập nhật profile
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UserProfileDTO updatedProfile) {
        try {
            UserProfileDTO updated = userService.updateUserProfile(updatedProfile);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            // Log lỗi
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    @GetMapping("/{userId}/stats")
//    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
//    public ResponseEntity<Map<String, Object>> getUserStats(@PathVariable Long userId) {
//        Map<String, Object> stats = userService.getUserStats(userId);
//        return ResponseEntity.ok(stats);
//    }
}
