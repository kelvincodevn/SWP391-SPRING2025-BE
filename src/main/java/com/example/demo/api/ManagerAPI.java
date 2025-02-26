package com.example.demo.api;

import com.example.demo.entity.Tests;
import com.example.demo.entity.User;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.service.ManagerService;
import com.example.demo.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/Manager/dashboard")
@SecurityRequirement(name = "api")
public class ManagerAPI {

    @Autowired
    ManagerService managerService;

    @Autowired
    private TestService testService;

    @GetMapping
    public List<Tests> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{testsId}")
    public ResponseEntity<?> getTestDetails(@PathVariable Long testsId) {
        return testService.getTestDetails(testsId);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createTest(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is required.");
            }

            // Gọi service để xử lý file Excel và tạo bài test
            ResponseEntity<String> uploadResponse = testService.uploadExcelFile(file);
            if (!uploadResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to process Excel file.");
            }

            return ResponseEntity.ok("Test uploaded successfully from Excel!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id) {
        return testService.deleteTest(id);
    }

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
