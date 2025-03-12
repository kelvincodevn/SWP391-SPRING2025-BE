package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_scoring")
public class TestScoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "test_id", nullable = false)
    private Tests test; // Khóa ngoại liên kết với bảng Tests

    @Column(nullable = false)
    private Integer minScore;

    @Column(nullable = true) // maxScore có thể null (ví dụ: mức cao nhất)
    private Integer maxScore;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false, length = 1000) // Tăng độ dài description
    private String description;

    public TestScoring() {
    }

    public TestScoring(Tests test, Integer minScore, Integer maxScore, String level, String description) {
        this.test = test;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.level = level;
        this.description = description;
    }

    // Getters và setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tests getTest() {
        return test;
    }

    public void setTest(Tests test) {
        this.test = test;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
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
