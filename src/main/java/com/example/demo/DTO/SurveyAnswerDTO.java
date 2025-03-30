package com.example.demo.DTO;

import lombok.Data;

@Data
public class SurveyAnswerDTO {
    private Long questionId;
    private String answerText;
}