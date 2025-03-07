package be.mentalhealth.springboot_backend.Repository;

import be.mentalhealth.springboot_backend.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
