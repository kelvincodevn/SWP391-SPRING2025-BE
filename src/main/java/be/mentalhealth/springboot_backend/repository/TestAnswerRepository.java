package be.mentalhealth.springboot_backend.repository;


import be.mentalhealth.springboot_backend.entity.SetOfQuestions;
import be.mentalhealth.springboot_backend.entity.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
    List<TestAnswer> findByQuestionIn(List<SetOfQuestions> questions);
}
