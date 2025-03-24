package be.mentalhealth.springboot_backend.DTO;

import java.util.List;

public class UserAnswersRequestDTO {
    private Long testId;
    private List<UserAnswerDTO> answers;

    // Getters & Setters
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public List<UserAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswerDTO> answers) {
        this.answers = answers;
    }
}

