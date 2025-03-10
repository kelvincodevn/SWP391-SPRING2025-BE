package be.mentalhealth.springboot_backend.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "set_of_questions")

public class SetOfQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private long questionId;

    private String questionText;
    private Integer questionNumber;
    private Integer maxScore;

    @ManyToOne(cascade = CascadeType.PERSIST) // Thêm cascade ở đây
    @JoinColumn(name = "tests_id", nullable = false) // Khóa ngoại tham chiếu tới tests
    private Tests tests;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestAnswer> answers;

    @Version
    private int version;

    public SetOfQuestions(long questionId, String questionText, Integer questionNumber, Integer maxScore, Tests tests, List<TestAnswer> answers, int version) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionNumber = questionNumber;
        this.maxScore = maxScore;
        this.tests = tests;
        this.answers = answers;
        this.version = version;
    }

    public SetOfQuestions() {
    }

    public List<TestAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<TestAnswer> answers) {
        this.answers = answers;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

}
