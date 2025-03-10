package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.entity.Survey;
import be.mentalhealth.springboot_backend.service.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class SurveyViewAPI {
    private final SurveyService surveyService;

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }
}

