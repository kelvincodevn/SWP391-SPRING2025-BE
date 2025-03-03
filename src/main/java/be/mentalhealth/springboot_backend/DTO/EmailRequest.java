package be.mentalhealth.springboot_backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {
    private String email;
    private String surveyLink;

    // Constructors
    public EmailRequest() {}

    public EmailRequest(String email, String surveyLink) {
        this.email = email;
        this.surveyLink = surveyLink;
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurveyLink() {
        return surveyLink;
    }

    public void setSurveyLink(String surveyLink) {
        this.surveyLink = surveyLink;
    }
}
