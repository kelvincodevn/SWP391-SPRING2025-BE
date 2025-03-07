package be.mentalhealth.springboot_backend.DTO;

import java.util.List;

public class TestListDTO {
    private Long testId;
    private String testName;
    private String testDescription;
    private int totalQuestions;
    private List<QuestionDTO> questions; // Using QuestionDTO to hold grouped answers

    public TestListDTO(Long testId, String testName, String testDescription, int totalQuestions, List<QuestionDTO> questions) {
        this.testId = testId;
        this.testName = testName;
        this.testDescription = testDescription;
        this.totalQuestions = totalQuestions;
        this.questions = questions;
    }

    // Getters and Setters
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}