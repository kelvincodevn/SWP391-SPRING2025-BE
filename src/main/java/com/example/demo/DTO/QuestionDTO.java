package com.example.demo.DTO;

import java.util.List;

public class QuestionDTO {
    private int questionNumber;
    private String questionText;
    private List<AnswerDTO> answers;

    public QuestionDTO(int questionNumber, String questionText, List<AnswerDTO> answers) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.answers = answers;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    // Getters & Setters
}

