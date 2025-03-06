package com.example.demo.DTO;

public class UserAnswerDTO {
    private Long questionId;
    private String answerText;

    public UserAnswerDTO(Long questionId, String answerText) {
        this.questionId = questionId;
        this.answerText = answerText;
    }

    public UserAnswerDTO() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
