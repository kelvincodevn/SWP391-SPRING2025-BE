package com.example.demo.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

    @Entity
    @Table(name = "test")
    public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long testId;

    private String testName;
    private String testDescription;

        public Test(String testName, String testDescription) {
            this.testName = testName;
            this.testDescription = testDescription;
        }

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SetOfQuestions> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestResult> results;

    public Test() {
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }
}
