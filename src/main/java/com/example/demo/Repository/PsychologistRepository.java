package com.example.demo.Repository;

import com.example.demo.entity.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PsychologistRepository extends JpaRepository<Psychologist, Integer> {
    List<Psychologist> findByStatusTrue();
}
