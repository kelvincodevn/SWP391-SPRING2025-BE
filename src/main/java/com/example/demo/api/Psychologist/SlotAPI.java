package com.example.demo.api.Psychologist;

import com.example.demo.entity.PsychologistSlot;
import com.example.demo.service.SlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<PsychologistSlot> createSlot(@RequestParam Long psychologistId,
                                                       @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
                                                       @RequestParam String startTime,
                                                       @RequestParam String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        return ResponseEntity.ok(slotService.createSlot(psychologistId, date, start, end));
    }

    @GetMapping
    public ResponseEntity<List<PsychologistSlot>> getPsychologistSlots(@RequestParam Long psychologistId) {
        return ResponseEntity.ok(slotService.getPsychologistSlots(psychologistId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSlot(@RequestParam Long psychologistId,
                                             @RequestParam Integer psychologistSlotId,
                                             @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
                                             @RequestParam String startTime,
                                             @RequestParam String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        slotService.updateSlot(psychologistId, psychologistSlotId, date, start, end);
        return ResponseEntity.ok("Slot updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSlot(@RequestParam Long psychologistId, @RequestParam Integer psychologistSlotId) {
        slotService.deleteSlot(psychologistId, psychologistSlotId);
        return ResponseEntity.ok("Slot deleted successfully");
    }

    @GetMapping("/available")
    public ResponseEntity<List<PsychologistSlot>> getAvailableSlots(@RequestParam Long psychologistId) {
        return ResponseEntity.ok(slotService.getAvailableSlots(psychologistId));
    }
}

