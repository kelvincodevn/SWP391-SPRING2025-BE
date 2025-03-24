package be.mentalhealth.springboot_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TestHistory")
public class TestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @Column(nullable = false)
    private String testVersion;

    @ManyToOne
    @JoinColumn(name = "resultId", nullable = false)
    private TestResult testResult;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ✅ One TestHistory can have multiple answers (One-to-Many)
    @OneToMany(mappedBy = "testHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestAnswerDetail> answers;

    // Constructors


    public TestHistory(Long historyId, String testVersion, TestResult testResult, User user, List<TestAnswerDetail> answers) {
        this.historyId = historyId;
        this.testVersion = testVersion;
        this.testResult = testResult;
        this.user = user;
        this.answers = answers;
    }

    public TestHistory() {
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getTestVersion() {
        return testVersion;
    }

    public void setTestVersion(String testVersion) {
        this.testVersion = testVersion;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TestAnswerDetail> getAnswers() {
        return answers;
    }

    public void setAnswers(List<TestAnswerDetail> answers) {
        this.answers = answers;
    }
}



