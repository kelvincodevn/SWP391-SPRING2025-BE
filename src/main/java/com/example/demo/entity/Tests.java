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
    @Column(name = "testid", nullable = false)
    private long id;

    @Column(name = "testname")
    private String testsName;

    @Column(name = "testdescription")
    private String testsDescription;

    @Version
    private Integer version;

    public Tests(Long maxTestId, String testsName, String testsDescription) {
        this.testsName = testsName;
        this.testsDescription = testsDescription;
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
