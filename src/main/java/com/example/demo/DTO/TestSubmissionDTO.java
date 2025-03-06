package com.example.demo.DTO;

import java.util.List;

public class TestSubmissionDTO {
    private Long testId;
    private List<UserAnswerDTO> answers;

    public TestSubmissionDTO(Long testId, List<UserAnswerDTO> answers) {
        this.testId = testId;
        this.answers = answers;
    }

    public TestSubmissionDTO() {
    }

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
