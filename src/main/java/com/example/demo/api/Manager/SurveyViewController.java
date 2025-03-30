package com.example.demo.api.Manager;


import com.example.demo.entity.Survey;
import com.example.demo.service.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manager/surveys")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class SurveyViewController {
    private final SurveyService surveyService;

    @PreAuthorize("hasAuthority('MANAGER')") // Chỉ MANAGER mới có quyền truy cập

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }
}