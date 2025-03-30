package com.example.demo.api.Manager;

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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            // Parse ISO 8601 datetime string with timezone
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime stTime = ZonedDateTime.parse(startTime, formatter).toLocalDateTime();
            LocalDateTime eTime = endTime != null ? ZonedDateTime.parse(endTime, formatter).toLocalDateTime() : null;

            ScheduleType st = ScheduleType.valueOf(scheduleType);
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