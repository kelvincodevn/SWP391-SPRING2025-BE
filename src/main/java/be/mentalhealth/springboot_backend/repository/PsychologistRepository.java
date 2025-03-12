package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PsychologistRepository extends JpaRepository<Psychologist, Integer> {
    List<Psychologist> findByStatusTrue();
}
