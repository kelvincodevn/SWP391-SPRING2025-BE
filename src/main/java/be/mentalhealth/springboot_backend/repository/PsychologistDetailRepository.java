package be.mentalhealth.springboot_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.mentalhealth.springboot_backend.entity.PsychologistDetail;

@Repository
public interface PsychologistDetailRepository extends JpaRepository<PsychologistDetail, Integer> {
    Optional<PsychologistDetail> findByPsychologist_PsychoId(Integer psychoId);
}

