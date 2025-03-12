package com.example.demo.Repository;

import com.example.demo.entity.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    List<TestHistory> findByUser_UserID(Long userID);

    Optional<TestHistory> findByTestResult_ResultIdAndUser_UserID(Long resultId, Long userId);

}
