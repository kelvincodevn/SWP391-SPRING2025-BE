package com.example.demo.Repository;

import com.example.demo.entity.PsychologistDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PsychologistDetailRepository extends JpaRepository<PsychologistDetail, Integer> {
    Optional<PsychologistDetail> findByPsychologist_PsychoId(Integer psychoId);
}

