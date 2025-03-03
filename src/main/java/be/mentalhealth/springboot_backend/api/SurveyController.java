package be.mentalhealth.springboot_backend.api;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import be.mentalhealth.springboot_backend.DTO.SurveyRequest;
import be.mentalhealth.springboot_backend.entity.SurveyEmailLog;
>>>>>>> Stashed changes
=======
import be.mentalhealth.springboot_backend.DTO.SurveyRequest;
import be.mentalhealth.springboot_backend.entity.SurveyEmailLog;
>>>>>>> Stashed changes
import be.mentalhealth.springboot_backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import java.util.List;

>>>>>>> Stashed changes
=======
import java.util.List;

>>>>>>> Stashed changes
@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyController {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSurvey(@RequestParam String email, @RequestParam String surveyLink) {
        emailService.sendSurveyEmail(email, surveyLink);
        return ResponseEntity.ok("Survey link sent to " + email);
=======
=======
>>>>>>> Stashed changes

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSurvey(@RequestBody SurveyRequest request) {
        emailService.sendSurveyEmails(request.getTitle(), request.getDescription(), request.getEmails(), request.getSurveyLink());
        return ResponseEntity.ok("Survey emails sent successfully.");
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    }
}
