package com.example.demo.Repository;

import com.example.demo.DTO.SurveyEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyEmailLogRepository extends JpaRepository<SurveyEmailLog, Long> {
}
