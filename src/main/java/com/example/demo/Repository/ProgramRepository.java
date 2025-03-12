package com.example.demo.Repository;


import com.example.demo.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository <Program, Long> {
    Program findProgramByprogramID(long id);

}
