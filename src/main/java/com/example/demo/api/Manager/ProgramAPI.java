package com.example.demo.api.Manager;

import com.example.demo.DTO.ProgramDTO;
import com.example.demo.DTO.ProgramViewDTO;
import com.example.demo.entity.Program;
import com.example.demo.service.ProgramService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/manager/program")
@SecurityRequirement(name = "api")
public class ProgramAPI {
    @Autowired
    ProgramService programService;

    @PreAuthorize("hasAuthority('MANAGER')") // Chỉ MANAGER amới có quyền truy cập
    @PostMapping
    public ResponseEntity createProgram(@Valid @RequestBody ProgramDTO program){
        Program newProgram = programService.createProgram(program);
        return ResponseEntity.ok(program);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping
    public ResponseEntity getAllProgramForManager(){
        List<ProgramViewDTO> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("{id}")
    public ResponseEntity deleteProgram(@PathVariable long id){
        Program program = programService.deleteProgram(id);
        return ResponseEntity.ok(program);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    public Program updateProgram(@PathVariable Long id, @RequestBody Program program) {
        return programService.updateProgram(id, program);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProgramDetailsForManager(@PathVariable Long id) {
        return programService.getProgramDetails(id);
    }
}
