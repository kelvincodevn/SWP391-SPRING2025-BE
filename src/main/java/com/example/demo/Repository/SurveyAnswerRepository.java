package com.example.demo.Repository;

import com.example.demo.entity.SurveyAnswer;
import com.example.demo.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
    List<SurveyAnswer> findByResponse(SurveyResponse response);
}