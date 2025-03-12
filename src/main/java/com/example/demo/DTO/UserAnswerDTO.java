package com.example.demo.DTO;

public class UserAnswerDTO {
    private Long questionId;
    private String answerText;

    private Integer score;

    public UserAnswerDTO(Long questionId, String answerText, Integer score) {
        this.questionId = questionId;
        this.answerText = answerText;
        this.score = score;
    }
    public UserAnswerDTO() {
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
