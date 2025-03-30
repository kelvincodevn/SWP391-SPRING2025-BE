package com.example.demo.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SurveyRequestDTO {
    private String title;
    private String description;
    private String scheduleType; // "ONE_TIME" or "RECURRING"
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String recurrenceInterval; // e.g., "MONTHLY"
    private List<SurveyQuestionDTO> questions;
}