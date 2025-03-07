package be.mentalhealth.springboot_backend.DTO;

public class AnswerDTO {
    private String answerText;
    private int score;

    public AnswerDTO(String answerText, int score) {
        this.answerText = answerText;
        this.score = score;
    }

    // Getters and Setters
    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
