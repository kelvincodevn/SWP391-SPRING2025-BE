package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.SurveyEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyEmailLogRepository extends JpaRepository<SurveyEmailLog, Long> {
}

