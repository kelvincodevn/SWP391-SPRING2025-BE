package com.example.demo.Repository;

import com.example.demo.entity.SetOfQuestions;
import com.example.demo.entity.Tests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetOfQuestionsRepository extends JpaRepository<SetOfQuestions, Long> {
    List<SetOfQuestions> findByTests(Tests tests);
    List<SetOfQuestions> findByTestsId(Long testsId);
}
