package com.example.demo.Repository;

import com.example.demo.entity.SetOfQuestions;
import com.example.demo.entity.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
    List<TestAnswer> findByQuestionIn(List<SetOfQuestions> questions);
    Optional<TestAnswer> findByQuestionAndAnswer(SetOfQuestions question, String answer);

}
