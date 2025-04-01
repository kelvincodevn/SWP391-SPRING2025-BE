package com.example.demo.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
public class Tests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String testsName;
    private String testsDescription;

    public Tests(long id, String testsName, String testsDescription, boolean isDeleted, List<SetOfQuestions> questions) {
            this.id = id;
            this.testsName = testsName;
            this.testsDescription = testsDescription;
            this.isDeleted = isDeleted;

            this.questions = questions;
        }
    
    public Tests() {
    }

    @JsonIgnore
    @OneToMany(mappedBy = "tests", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetOfQuestions> questions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestScoring> scorings = new ArrayList<>(); // Thêm relationship với TestScoring

    public Tests() {
    }

    public long getId() {
        return id;
    }

    public void setId(long testsId) {
        this.id = testsId;
    }

    public String getTestsName() {
        return testsName;
    }

    public void setTestsName(String testsName) {
        this.testsName = testsName;
    }

    public String getTestsDescription() {
        return testsDescription;
    }

    public void setTestsDescription(String testsDescription) {
        this.testsDescription = testsDescription;
    }
}
