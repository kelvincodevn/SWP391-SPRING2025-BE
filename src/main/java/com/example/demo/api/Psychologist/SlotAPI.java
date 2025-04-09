package com.example.demo.api.Psychologist;

import com.example.demo.entity.Slot;
import com.example.demo.service.SlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/psychologist/slots")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('PSYCHOLOGIST')")
public class SlotAPI {
    private final SlotService slotService;

    public SlotAPI(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSlot(
            @RequestParam Long psychologistId,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            Slot slot = slotService.createSlot(psychologistId, date, start, end);
            return ResponseEntity.ok(slot);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the slot");
        }
    }

    @GetMapping
    public ResponseEntity<List<Slot>> getPsychologistSlots(@RequestParam Long psychologistId) {
        return ResponseEntity.ok(slotService.getPsychologistSlots(psychologistId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSlot(
            @RequestParam Long psychologistId,
            @RequestParam Integer slotId,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            slotService.updateSlot(psychologistId, slotId, date, start, end);
            return ResponseEntity.ok("Slot updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the slot");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSlot(@RequestParam Long psychologistId, @RequestParam Integer slotId) {
        return slotService.deleteSlot(psychologistId, slotId);
    }
}