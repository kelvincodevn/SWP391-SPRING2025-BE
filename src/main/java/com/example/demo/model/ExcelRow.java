package com.example.demo.model;

public class ExcelRow {
    private String testName;

    private String questionText;
    private String answerText;
    private int maxScore;

    public ExcelRow(String testName, String questionText, String answerText, int maxScore) {
        this.testName = testName;

        this.questionText = questionText;
        this.answerText = answerText;
        this.maxScore = maxScore;

    }

    // Getters và Setters
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }



    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }


}


