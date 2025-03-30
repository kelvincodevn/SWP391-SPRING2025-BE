package com.example.demo.Repository;

import com.example.demo.entity.SurveyAnswerOption;
import com.example.demo.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyAnswerOptionRepository extends JpaRepository<SurveyAnswerOption, Long> {
    List<SurveyAnswerOption> findByQuestion(SurveyQuestion question);
}