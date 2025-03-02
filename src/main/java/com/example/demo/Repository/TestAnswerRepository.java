package com.example.demo.Repository;

import com.example.demo.entity.SetOfQuestions;
import com.example.demo.entity.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
    List<TestAnswer> findByQuestionIn(List<SetOfQuestions> questions);
}
