package com.example.demo.DTO;

import lombok.Data;
import java.util.List;

@Data
public class SurveyResponseDTO {
    private Long surveyId;
    private List<SurveyAnswerDTO> answers;
}