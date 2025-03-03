package be.mentalhealth.springboot_backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SurveyRequest {
    private String title;
    private String description;
    private String surveyLink;
    private List<String> emails; // Danh sách email người nhận survey
}
