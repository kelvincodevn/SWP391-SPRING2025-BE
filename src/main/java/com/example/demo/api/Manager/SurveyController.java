package com.example.demo.api.Manager;

import com.example.demo.DTO.SurveyRequest;
import com.example.demo.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/survey")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class SurveyController {

    private final EmailService emailService;

    @PreAuthorize("hasAuthority('MANAGER')") // Chỉ MANAGER mới có quyền truy cập

    @PostMapping("/send")
    public ResponseEntity<String> sendSurvey(@RequestBody SurveyRequest request) {
        emailService.sendSurveyEmails(request.getTitle(), request.getDescription(), request.getEmails(), request.getSurveyLink());
        return ResponseEntity.ok("Survey emails sent successfully.");
    }
}
