package be.mentalhealth.springboot_backend.DTO;

public class TestAnswerDTO {
    private int questionNumber;
    private String questionText;
    private String answerText;
    private int score;

    public TestAnswerDTO(int questionNumber, String questionText, String answerText, int score) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.answerText = answerText;
        this.score = score;
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

    // Getters & Setters
}
