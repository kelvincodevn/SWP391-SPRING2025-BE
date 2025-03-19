package com.example.demo.Repository;

import com.example.demo.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    List<TestResult> findByUser_UserID(Long userId);
}
