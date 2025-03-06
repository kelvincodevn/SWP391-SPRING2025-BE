package com.example.demo.DTO;

import java.util.List;

public class TestDetailsDTO {
    private Long testId;
    private String testName;
    private String testType;
    private int totalQuestions;
    private List<TestAnswerDTO> answers;

    public TestDetailsDTO(Long testId, String testName, String testType, int totalQuestions, List<TestAnswerDTO> answers) {
        this.testId = testId;
        this.testName = testName;
        this.testType = testType;
        this.totalQuestions = totalQuestions;
        this.answers = answers;
    }

    // Getters & Setters

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

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<TestAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<TestAnswerDTO> answers) {
        this.answers = answers;
    }
}
