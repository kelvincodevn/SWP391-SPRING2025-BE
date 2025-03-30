package com.example.demo.Repository;

import com.example.demo.entity.Survey;
import com.example.demo.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
    List<SurveyQuestion> findBySurvey(Survey survey);
}