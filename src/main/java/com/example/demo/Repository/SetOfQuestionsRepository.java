package com.example.demo.Repository;

import com.example.demo.entity.SetOfQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetOfQuestionsRepository extends JpaRepository<SetOfQuestions, Long> {
}
