package com.example.demo.api.User;

import com.example.demo.DTO.SurveyResponseDTO;
import com.example.demo.entity.Survey;
import com.example.demo.entity.SurveyAnswerOption;
import com.example.demo.entity.SurveyQuestion;
import com.example.demo.entity.SurveyResponse;
import com.example.demo.entity.User;
import com.example.demo.service.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/survey")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class UserSurveyController {
    private final SurveyService surveyService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllSurveys() {
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

//    @GetMapping("/{surveyId}")
//    public ResponseEntity<Map<String, Object>> getSurveyDetails(@PathVariable Long surveyId) {
//        try {
//            Survey survey = surveyService.getSurveyDetails(surveyId);
//            List<SurveyQuestion> questions = survey.getQuestions();
//
//            List<Map<String, Object>> questionList = questions.stream().map(q -> {
//                List<SurveyAnswerOption> answerOptions = q.getAnswerOptions();
//                List<Map<String, Object>> answers = answerOptions.stream()
//                        .map(a -> Map.of("answerText", a.getAnswerText()))
//                        .collect(Collectors.toList());
//
//                return Map.of(
//                        "questionNumber", q.getQuestionNumber(),
//                        "questionText", q.getQuestionText(),
//                        "answers", answers
//                );
//            }).collect(Collectors.toList());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("surveyId", survey.getId());
//            response.put("surveyName", survey.getSurveyName());
//            response.put("surveyDescription", survey.getSurveyDescription());
//            response.put("totalQuestions", questions.size());
//            response.put("questions", questionList);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            throw new RuntimeException("Error fetching survey details: " + e.getMessage());
//        }
//    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<Map<String, Object>> getSurveyDetails(@PathVariable Long surveyId) {
        try {
            Survey survey = surveyService.getSurveyDetails(surveyId);
            List<SurveyQuestion> questions = survey.getQuestions();

            List<Map<String, Object>> questionList = questions.stream().map(q -> {
                List<SurveyAnswerOption> answerOptions = q.getAnswerOptions();
                List<Map<String, Object>> answers = answerOptions.stream()
                        .map(a -> {
                            Map<String, Object> answerMap = new HashMap<>();
                            answerMap.put("answerText", a.getAnswerText());
                            return answerMap;
                        })
                        .collect(Collectors.toList());

                Map<String, Object> questionMap = new HashMap<>();
                questionMap.put("questionNumber", q.getQuestionNumber());
                questionMap.put("questionText", q.getQuestionText());
                questionMap.put("answers", answers);
                return questionMap;
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("surveyId", survey.getId());
            response.put("surveyName", survey.getSurveyName());
            response.put("surveyDescription", survey.getSurveyDescription());
            response.put("totalQuestions", questions.size());
            response.put("questions", questionList);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching survey details: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<String> submitSurvey(
            @PathVariable Long id,
            @RequestBody SurveyResponseDTO request,
            @AuthenticationPrincipal User user) {
        try {
            request.setSurveyId(id);
            SurveyResponse response = surveyService.submitSurveyResponse(request, user);
            return ResponseEntity.ok("Survey submitted successfully");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error submitting survey: " + e.getMessage());
        }
    }

//    @GetMapping("/history")
//    public ResponseEntity<List<Map<String, Object>>> getUserSurveyHistory(@AuthenticationPrincipal User user) {
//        try {
//            List<SurveyResponse> history = surveyService.getUserSurveyHistory(user);
//            List<Map<String, Object>> response = history.stream().map(resp -> Map.of(
//                    "responseId", resp.getId(),
//                    "surveyName", resp.getSurvey().getSurveyName(),
//                    "submittedAt", resp.getSubmittedAt().toString()
//            )).collect(Collectors.toList());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            throw new RuntimeException("Error fetching survey history: " + e.getMessage());
//        }
//    }

    @GetMapping("/history")
    public ResponseEntity<List<Map<String, Object>>> getUserSurveyHistory(@AuthenticationPrincipal User user) {
        try {
            List<SurveyResponse> history = surveyService.getUserSurveyHistory(user);
            List<Map<String, Object>> response = history.stream().map(resp -> {
                Map<String, Object> map = new HashMap<>();
                map.put("responseId", resp.getId());
                map.put("surveyName", resp.getSurvey().getSurveyName());
                map.put("submittedAt", resp.getSubmittedAt().toString());
                return map;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching survey history: " + e.getMessage());
        }
    }

//    @GetMapping("/history/{responseId}")
//    public ResponseEntity<Map<String, Object>> getSurveyHistoryDetails(@PathVariable Long responseId) {
//        try {
//            SurveyResponse response = surveyService.getSurveyResponseDetails(responseId);
//            List<Map<String, Object>> answers = response.getAnswers().stream().map(answer -> Map.of(
//                    "questionText", answer.getQuestion().getQuestionText(),
//                    "answerText", answer.getAnswerText()
//            )).collect(Collectors.toList());
//
//            return ResponseEntity.ok(Map.of(
//                    "surveyName", response.getSurvey().getSurveyName(),
//                    "answers", answers,
//                    "submittedAt", response.getSubmittedAt().toString()
//            ));
//        } catch (Exception e) {
//            throw new RuntimeException("Error fetching survey history details: " + e.getMessage());
//        }
//    }

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
            responseMap.put("answers", answers);
            responseMap.put("submittedAt", response.getSubmittedAt().toString());

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching survey history details: " + e.getMessage());
        }
    }
}