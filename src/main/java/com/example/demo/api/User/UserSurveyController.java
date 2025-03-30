package com.example.demo.api.User;

import com.example.demo.DTO.SurveyResponseDTO;
import com.example.demo.entity.Survey;
import com.example.demo.entity.SurveyResponse;
import com.example.demo.entity.User;
import com.example.demo.service.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/survey")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class UserSurveyController {
    private final SurveyService surveyService;

    @GetMapping("/active")
    public ResponseEntity<?> getActiveSurveys() {
        try {
            List<Survey> surveys = surveyService.getActiveSurveys();
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching active surveys: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitSurvey(
            @PathVariable Long id,
            @RequestBody SurveyResponseDTO request,
            @AuthenticationPrincipal User user) {
        try {
            request.setSurveyId(id);
            SurveyResponse response = surveyService.submitSurveyResponse(request, user);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting survey: " + e.getMessage());
        }
    }
}