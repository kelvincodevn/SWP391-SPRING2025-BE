package com.example.demo.DTO;

import lombok.Data;
import java.util.List;

@Data
public class SurveyQuestionDTO {
    private String questionText;
    private String questionType; // "TEXT", "MULTIPLE_CHOICE", "RATING"
    private List<String> options; // For MULTIPLE_CHOICE or RATING
}