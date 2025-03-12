package com.example.demo.DTO;

public class TestScoringDTO {
    private Integer minScore;
    private Integer maxScore;
    private String level;
    private String description;

    public TestScoringDTO(Integer minScore, Integer maxScore, String level, String description) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.level = level;
        this.description = description;
    }

    public TestScoringDTO() {
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
