package com.example.demo.api.Manager;

import com.example.demo.entity.Survey;
import com.example.demo.entity.SurveyResponse;
import com.example.demo.entity.User;
import com.example.demo.service.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manager/survey")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createSurvey(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User manager) {
        try {
            Survey survey = surveyService.createSurveyFromExcel(file, manager);
            return ResponseEntity.ok("Survey created successfully with ID: " + survey.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating survey: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllSurveys() { // Chỉ định rõ kiểu trả về
        try {
            List<Survey> surveys = surveyService.getAllSurveys();
            List<Map<String, Object>> response = surveys.stream().map(survey -> {
                Map<String, Object> surveyMap = new HashMap<>();
                surveyMap.put("id", survey.getId());
                surveyMap.put("surveyName", survey.getSurveyName());
                surveyMap.put("surveyDescription", survey.getSurveyDescription());
                return surveyMap;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching surveys: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<Map<String, Object>>> getAllSurveyHistory() {
        try {
            List<SurveyResponse> history = surveyService.getAllSurveyHistory();
            List<Map<String, Object>> response = history.stream().map(resp -> {
                Map<String, Object> map = new HashMap<>();
                map.put("responseId", resp.getId());
                map.put("surveyName", resp.getSurvey().getSurveyName());
                map.put("studentName", resp.getUser().getFullName());
                map.put("studentEmail", resp.getUser().getEmail());
                map.put("submittedAt", resp.getSubmittedAt().toString());
                return map;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching survey history: " + e.getMessage());
        }
    }

    @GetMapping("/history/{responseId}")
    public ResponseEntity<Map<String, Object>> getSurveyHistoryDetails(@PathVariable Long responseId) {
        try {
            SurveyResponse response = surveyService.getSurveyResponseDetails(responseId);
            List<Map<String, Object>> answers = response.getAnswers().stream().map(answer -> {
                Map<String, Object> answerMap = new HashMap<>();
                answerMap.put("questionText", answer.getQuestion().getQuestionText());
                answerMap.put("answerText", answer.getAnswerText());
                return answerMap;
            }).collect(Collectors.toList());

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("surveyName", response.getSurvey().getSurveyName());
            responseMap.put("studentName", response.getUser().getFullName());
            responseMap.put("studentEmail", response.getUser().getEmail());
            responseMap.put("answers", answers);
            responseMap.put("submittedAt", response.getSubmittedAt().toString());

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching survey history details: " + e.getMessage());
        }
    }
}