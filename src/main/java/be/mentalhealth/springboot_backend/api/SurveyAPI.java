package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.DTO.SurveyRequest;
import be.mentalhealth.springboot_backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyAPI {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSurvey(@RequestBody SurveyRequest request) {
        emailService.sendSurveyEmails(request.getTitle(), request.getDescription(), request.getEmails(), request.getSurveyLink());
        return ResponseEntity.ok("Survey emails sent successfully.");
    }
}
