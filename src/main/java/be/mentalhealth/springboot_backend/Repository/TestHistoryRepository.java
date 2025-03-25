package be.mentalhealth.springboot_backend.Repository;


import be.mentalhealth.springboot_backend.entity.TestHistory;
import be.mentalhealth.springboot_backend.entity.TestResult;
import be.mentalhealth.springboot_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    List<TestHistory> findByUser_UserID(Long userID);
    Optional<TestHistory> findByTestResult_ResultIdAndUser_UserID(Long resultId, Long userId);

    long countByTestResult_Test_TestsName(String testName);


}
