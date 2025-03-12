package be.mentalhealth.springboot_backend.service;


import be.mentalhealth.springboot_backend.entity.Program;
import be.mentalhealth.springboot_backend.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProgramService {

    @Autowired
    ProgramRepository programRepository;

    public List<Program> getAllProgram() {
        return programRepository.findAll();
    }

    public Program createProgram(Program program) {
        Program newProgram = null;  // Khai báo biến trước
        try {
            newProgram = programRepository.save(program);
        } catch (Exception e) {
            System.err.println("Error saving program: " + e.getMessage());
        }
        return newProgram;  // Trả về null nếu có lỗi
    }


    public Program deleteProgram(long id) {
        Program program = programRepository.findProgramByprogramID(id);
        program.isDeleted = true;
        return programRepository.save(program);
    }

    public Program updateProgram(Long id, Program program) {
        Optional<Program> optionalProgram = programRepository.findById(id);
        if (optionalProgram.isPresent()) {
            Program newProgram = optionalProgram.get();
            newProgram.setProgramName(program.getProgramName());
            newProgram.setProgramDescription(program.getProgramDescription());
            newProgram.setProgramCategory(program.getProgramCategory());
            return programRepository.save(newProgram);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
