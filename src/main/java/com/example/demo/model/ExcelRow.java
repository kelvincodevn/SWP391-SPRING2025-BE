package com.example.demo.model;

public class ExcelRow {
    private String testName;
    private int questionNumber; // Thêm questionNumber
    private String questionText;
    private String answerText;
    private int maxScore;
    private long questionId;
    private long resultId;

    public ExcelRow(String testName, int questionNumber, String questionText, String answerText, int maxScore, long questionId, long resultId) {
        this.testName = testName;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.answerText = answerText;
        this.maxScore = maxScore;
        this.questionId = questionId;
        this.resultId = resultId;
    }

    // Getters và Setters
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getQuestionNumber() { // Thêm getter cho questionNumber
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) { // Thêm setter cho questionNumber
        this.questionNumber = questionNumber;
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

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }
}


