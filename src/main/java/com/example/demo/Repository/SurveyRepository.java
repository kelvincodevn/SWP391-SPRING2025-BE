package com.example.demo.Repository;

import com.example.demo.entity.Survey;
import com.example.demo.enums.SurveyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByStatusAndStartTimeBeforeAndEndTimeAfter(
            SurveyStatus status, LocalDateTime before, LocalDateTime after);

    List<Survey> findAllByStatus(SurveyStatus status);
}