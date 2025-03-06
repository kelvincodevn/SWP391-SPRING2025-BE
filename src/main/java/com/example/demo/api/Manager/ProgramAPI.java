package com.example.demo.api.Manager;

import com.example.demo.entity.Program;
import com.example.demo.service.ProgramService;
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
    public ResponseEntity createProgram(@Valid @RequestBody Program program){
        Program newProgram = programService.createProgram(program);
        return ResponseEntity.ok(program);
    }

    @GetMapping("Manager")
    public ResponseEntity getAllProgram(){
        List<Program> programs = programService.getAllProgram();
        return ResponseEntity.ok(programs);
    }

    @DeleteMapping("Manager/{id}")
    public ResponseEntity deleteProgram(@PathVariable long id){
        Program program = programService.deleteProgram(id);
        return ResponseEntity.ok(program);
    }

    @PutMapping("Manager/{id}")
    public Program updateProgram(@PathVariable Long id, @RequestBody Program program) {
        return programService.updateProgram(id, program);
    }
}
