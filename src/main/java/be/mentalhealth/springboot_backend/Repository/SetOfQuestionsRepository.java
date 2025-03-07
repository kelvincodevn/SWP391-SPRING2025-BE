package be.mentalhealth.springboot_backend.Repository;

import be.mentalhealth.springboot_backend.entity.SetOfQuestions;
import be.mentalhealth.springboot_backend.entity.Tests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetOfQuestionsRepository extends JpaRepository<SetOfQuestions, Long> {
    List<SetOfQuestions> findByTests(Tests tests);
    List<SetOfQuestions> findByTestsId(Long testsId);
}
