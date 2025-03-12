package be.mentalhealth.springboot_backend.Repository;

import be.mentalhealth.springboot_backend.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByUserUsername(String username);
}
