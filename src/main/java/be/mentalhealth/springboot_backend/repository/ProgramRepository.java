package be.mentalhealth.springboot_backend.repository;


import be.mentalhealth.springboot_backend.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository <Program, Long> {
    Program findProgramByprogramID(long id);

}
