package be.mentalhealth.springboot_backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SurveyRequest {
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSurveyLink() {
        return surveyLink;
    }

    public void setSurveyLink(String surveyLink) {
        this.surveyLink = surveyLink;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    private String surveyLink;
    private List<String> emails; // Danh sách email người nhận survey
}
