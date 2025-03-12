package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TestAnswerDetail")
public class TestAnswerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer questionNo;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private SetOfQuestions question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "historyId", nullable = false)
    private TestHistory testHistory;

    // Constructors
    public TestAnswerDetail() {}

    public TestAnswerDetail(Integer questionNo, SetOfQuestions question, String answer, Integer score, TestHistory testHistory) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
        this.score = score;
        this.testHistory = testHistory;
    }

    // Getters and Setters
    public Integer getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }

    public SetOfQuestions getQuestion() {
        return question;
    }

    public void setQuestion(SetOfQuestions question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public TestHistory getTestHistory() {
        return testHistory;
    }

    public void setTestHistory(TestHistory testHistory) {
        this.testHistory = testHistory;
    }
}