package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.DTO.ProgramDTO;
import be.mentalhealth.springboot_backend.DTO.ProgramDetailDTO;
import be.mentalhealth.springboot_backend.DTO.ProgramViewDTO;
import be.mentalhealth.springboot_backend.Repository.ProgramRepository;
import be.mentalhealth.springboot_backend.entity.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramService {

    @Autowired
    ProgramRepository programRepository;

    public List<ProgramViewDTO> getAllPrograms() {
        List<Program> programs = programRepository.findByIsDeletedFalse();

        return programs.stream()
                .map(p -> new ProgramViewDTO(p.getProgramId() ,p.getProgramName(), p.getProgramCategory()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> getProgramDetails(Long programId) {
        Optional<Program> programOptional = programRepository.findById(programId);

        if (programOptional.isEmpty() || programOptional.get().isDeleted()) {
            return ResponseEntity.badRequest().body("Program not found or has been deleted.");
        }

        Program program = programOptional.get();
        ProgramDetailDTO programDetail = new ProgramDetailDTO(
                program.getProgramName(),
                program.getProgramCategory(),
                program.getProgramDescription(),
                program.getProgramLink()
        );

        return ResponseEntity.ok(programDetail);
    }



    public Program createProgram(ProgramDTO programDTO) {
        try {
            Program program = new Program();
            program.setProgramName(programDTO.getProgramName());
            program.setProgramCategory(programDTO.getProgramCategory());
            program.setProgramDescription(programDTO.getProgramDescription());
            program.setProgramLink(programDTO.getProgramLink());
            program.setDeleted(false);

            return programRepository.save(program);
        } catch (Exception e) {
            System.err.println("Error saving program: " + e.getMessage());
            return null; // Return null if there's an error
        }
    }




    public Program deleteProgram(long id) {
        Program program = programRepository.findProgramByprogramID(id);
        program.setDeleted(true);
        return programRepository.save(program);
    }

    public Program updateProgram(Long id, Program program) {
        Optional<Program> optionalProgram = programRepository.findById(id);
        if (optionalProgram.isPresent()) {
            Program newProgram = optionalProgram.get();
            newProgram.setProgramName(program.getProgramName());
            newProgram.setProgramDescription(program.getProgramDescription());
            newProgram.setProgramCategory(program.getProgramCategory());
            newProgram.setProgramLink(program.getProgramLink());
            return programRepository.save(newProgram);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
