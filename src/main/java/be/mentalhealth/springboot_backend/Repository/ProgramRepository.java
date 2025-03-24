package be.mentalhealth.springboot_backend.Repository;


import be.mentalhealth.springboot_backend.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository <Program, Long> {
    Program findProgramByprogramID(long id);

    List<Program> findByIsDeletedFalse();
}
