package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_answer")
public class TestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    private String answer;
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private SetOfQuestions question;

    @ManyToOne
    @JoinColumn(name = "resultId", nullable = false)
    private TestResult result;

    public TestAnswer() {
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
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

    public SetOfQuestions getQuestion() {
        return question;
    }

    public void setQuestion(SetOfQuestions question) {
        this.question = question;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }
}


