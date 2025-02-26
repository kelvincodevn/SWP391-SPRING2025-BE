package com.example.demo.service;

import com.example.demo.Repository.TestResultRepository;
import com.example.demo.entity.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    public List<TestResult> getResultsByUserId(Integer userId) {
        return testResultRepository.findAll().stream()
                .filter(result -> result.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
