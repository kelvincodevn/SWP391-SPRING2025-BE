package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
