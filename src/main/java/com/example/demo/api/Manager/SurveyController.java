package com.example.demo.api.Manager;

import com.example.demo.DTO.SurveyRequestDTO;
import com.example.demo.entity.Survey;
import com.example.demo.entity.User;
import com.example.demo.enums.ScheduleType;
import com.example.demo.service.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/manager/survey")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<?> createSurvey(
            @RequestParam("file") MultipartFile file,
            @RequestParam("scheduleType") String scheduleType,
            @RequestParam("startTime") String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "recurrenceInterval", required = false) String recurrenceInterval,
            @AuthenticationPrincipal User manager) {
        try {
            ScheduleType st = ScheduleType.valueOf(scheduleType);
            LocalDateTime stTime = LocalDateTime.parse(startTime);
            LocalDateTime eTime = endTime != null ? LocalDateTime.parse(endTime) : null;
            Survey survey = surveyService.createSurveyFromExcel(file, manager, st, stTime, eTime, recurrenceInterval);
            return ResponseEntity.ok(survey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating survey: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllSurveys() {
        try {
            List<Survey> surveys = surveyService.getAllSurveys();
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching surveys: " + e.getMessage());
        }
    }
}