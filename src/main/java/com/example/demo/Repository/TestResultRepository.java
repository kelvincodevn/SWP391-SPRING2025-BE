package com.example.demo.Repository;

import com.example.demo.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    List<TestResult> findByUser_UserID(Long userId);

    // Sửa từ completedAt thành createAt
    Optional<TestResult> findTopByUser_UserIDOrderByCreateAtDesc(Long userId);
}
