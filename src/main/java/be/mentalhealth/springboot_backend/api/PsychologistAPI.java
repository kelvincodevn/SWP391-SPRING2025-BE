package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.DTO.CreatePsychologistDTO;
import be.mentalhealth.springboot_backend.DTO.PsychologistDTO;
import be.mentalhealth.springboot_backend.DTO.PsychologistDetailDTO;
import be.mentalhealth.springboot_backend.entity.Psychologist;
import be.mentalhealth.springboot_backend.service.PsychologistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/psychologists")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class PsychologistAPI {
    private final PsychologistService psychologistService;


    public PsychologistAPI(PsychologistService psychologistService) {
        this.psychologistService = psychologistService;
    }

    @GetMapping
    public ResponseEntity<List<Psychologist>> getAllPsychologists() {
        return ResponseEntity.ok(psychologistService.getAllPsychologists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Psychologist> getPsychologistById(@PathVariable Integer id) {
        try {
            Psychologist psychologist = psychologistService.getPsychologistById(id);
            return ResponseEntity.ok(psychologist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Psychologist> createPsychologist(
            @RequestBody @Valid CreatePsychologistDTO dto) {
        return ResponseEntity.ok(psychologistService.createPsychologist(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Psychologist> updatePsychologist(
            @PathVariable Integer id,
            @Valid @RequestBody PsychologistDTO psychologistDTO
    ) {
        try {
            Psychologist updatedPsychologist = psychologistService.updatePsychologist(id, psychologistDTO);
            return ResponseEntity.ok(updatedPsychologist);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePsychologist(@PathVariable Integer id) {
        try {
            psychologistService.softDeletePsychologist(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/{id}/detail")
    public ResponseEntity<PsychologistDetailDTO> getPsychologistDetailOnly(@PathVariable Integer id) {
        return ResponseEntity.ok(psychologistService.getPsychologistDetailOnly(id));
    }

}
