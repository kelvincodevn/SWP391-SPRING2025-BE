package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.DTO.ProgramDTO;
import be.mentalhealth.springboot_backend.DTO.ProgramViewDTO;
import be.mentalhealth.springboot_backend.service.ProgramService;
import be.mentalhealth.springboot_backend.entity.Program;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/Program")
@SecurityRequirement(name = "api")
public class ProgramAPI {
    @Autowired
    ProgramService programService;

    @PostMapping("Manager")
    public ResponseEntity createProgram(@Valid @RequestBody ProgramDTO program){
        Program newProgram = programService.createProgram(program);
        return ResponseEntity.ok(program);
    }

    @GetMapping("Manager")
    public ResponseEntity getAllProgramForManager(){
        List<ProgramViewDTO> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }

    @DeleteMapping("/Manager/{id}")
    public ResponseEntity deleteProgram(@PathVariable long id){
        Program program = programService.deleteProgram(id);
        return ResponseEntity.ok(program);
    }

    @PutMapping("/Manager/{id}")
    public Program updateProgram(@PathVariable Long id, @RequestBody Program program) {
        return programService.updateProgram(id, program);
    }

    @GetMapping("User")
    public ResponseEntity getAllProgramForUser(){
        List<ProgramViewDTO> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }

    @GetMapping("User/{programId}")
    public ResponseEntity<?> getProgramDetailsForUser(@PathVariable Long programId) {
        return programService.getProgramDetails(programId);
    }

    @GetMapping("Manager/{programId}")
    public ResponseEntity<?> getProgramDetailsForManager(@PathVariable Long programId) {
        return programService.getProgramDetails(programId);
    }
}
