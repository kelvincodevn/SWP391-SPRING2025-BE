package com.example.demo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SurveyResponseDTO {
    private Long surveyId;
    private List<AnswerDTO> answers;

    @Data
    public static class AnswerDTO {
        private Integer questionId; // questionNumber
        private String answerText;
    }
}