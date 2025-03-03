package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}

