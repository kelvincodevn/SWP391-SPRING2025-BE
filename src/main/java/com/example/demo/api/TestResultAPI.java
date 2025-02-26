package com.example.demo.api;

import com.example.demo.entity.TestResult;
import com.example.demo.service.TestResultService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Manager/testresults")
@CrossOrigin
@SecurityRequirement(name = "api")
public class TestResultAPI {

    @Autowired
    private TestResultService testResultService;

    @GetMapping("/user/{userId}")
    public List<TestResult> getResultsByUserId(@PathVariable Integer userId) {
        return testResultService.getResultsByUserId(userId);
    }
}
