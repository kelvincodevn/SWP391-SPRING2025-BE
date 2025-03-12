package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TestResult")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @Column(nullable = false)
    private Integer totalScore;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private Tests test;

    // Getters and Setters


    public TestResult() {
    }

    public TestResult(Long resultId, Integer totalScore, LocalDateTime createAt, String level, String description, User user, Tests test) {
        this.resultId = resultId;
        this.totalScore = totalScore;
        this.createAt = createAt;
        this.level = level;
        this.description = description;
        this.user = user;
        this.test = test;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
