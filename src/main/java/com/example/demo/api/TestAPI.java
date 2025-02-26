package com.example.demo.api;

import com.example.demo.entity.Tests;
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
@RequestMapping("/api/tests")
@SecurityRequirement(name = "api")
public class TestAPI {

    @Autowired
    private TestService testService;

    @GetMapping("/all")
    public ResponseEntity<List<Tests>> getAllTests() {
        List<Tests> tests = testService.getAllTests();
        return ResponseEntity.ok(tests);
    }

}


