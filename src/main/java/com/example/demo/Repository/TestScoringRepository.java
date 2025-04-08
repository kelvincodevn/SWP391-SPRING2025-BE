package com.example.demo.Repository;

import com.example.demo.entity.TestScoring;
import com.example.demo.entity.Tests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestScoringRepository extends JpaRepository<TestScoring, Long> {
    List<TestScoring> findByTest(Tests test); // Thêm phương thức truy vấn
    // Thêm phương thức mới
    List<TestScoring> findByTest_Id(Long testId);
}
