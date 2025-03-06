package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TestHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Tests test;

    @Column(nullable = false)
    private Integer totalScore;

    @ElementCollection
    private List<String> submittedAnswers;  // Store selected answers as text

    @ElementCollection
    private List<Integer> answerScores;  // Store the corresponding scores

    private LocalDateTime submittedAt;

    public TestHistory(Long historyId, User user, Tests test, Integer totalScore, List<String> submittedAnswers, List<Integer> answerScores, LocalDateTime submittedAt) {
        this.historyId = historyId;
        this.user = user;
        this.test = test;
        this.totalScore = totalScore;
        this.submittedAnswers = submittedAnswers;
        this.answerScores = answerScores;
        this.submittedAt = submittedAt;
    }

    public TestHistory() {
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tests getTest() {
        return test;
    }

    public void setTest(Tests test) {
        this.test = test;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public List<String> getSubmittedAnswers() {
        return submittedAnswers;
    }

    public void setSubmittedAnswers(List<String> submittedAnswers) {
        this.submittedAnswers = submittedAnswers;
    }

    public List<Integer> getAnswerScores() {
        return answerScores;
    }

    public void setAnswerScores(List<Integer> answerScores) {
        this.answerScores = answerScores;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
