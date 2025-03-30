package com.example.demo.Repository;

import com.example.demo.entity.SurveyResponse;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    List<SurveyResponse> findByUser(User user); // Dùng cho student
    List<SurveyResponse> findByUser_UserID(Long userId); // Dùng cho manager/psychologist
}