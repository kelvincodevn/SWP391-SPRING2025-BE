package com.example.demo.api.User;

import com.example.demo.DTO.ProgramViewDTO;
import com.example.demo.service.ProgramService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/user/program")
@SecurityRequirement(name = "api")
public class UserProgramAPI {

    @Autowired
    ProgramService programService;

    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    @GetMapping
    public ResponseEntity getAllProgramForUser(){
        List<ProgramViewDTO> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    @GetMapping("/{programId}")
    public ResponseEntity<?> getProgramDetailsForUser(@PathVariable Long programId) {
        return programService.getProgramDetails(programId);
    }


}
