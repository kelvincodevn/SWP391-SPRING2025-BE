package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}