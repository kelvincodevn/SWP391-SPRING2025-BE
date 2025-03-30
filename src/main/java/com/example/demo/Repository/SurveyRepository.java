package com.example.demo.Repository;

import com.example.demo.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // Không cần findByStatusAndStartTimeBeforeAndEndTimeAfter nữa
    List<Survey> findAllByCreatedBy_UserID(Long userId); // Dùng cho manager
}