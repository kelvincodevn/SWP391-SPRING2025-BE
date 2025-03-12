package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.entity.PsychologistSlot;
import be.mentalhealth.springboot_backend.service.SlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/slots")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
public class SlotAPI {
    private final SlotService slotService;

<<<<<<< HEAD
    private static final Logger log = LoggerFactory.getLogger(SlotAPI.class);
    public SlotAPI(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/create")
    public ResponseEntity<PsychologistSlot> createSlot(@RequestParam Integer psychologistId,
                                                       @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
=======
    @PostMapping("/create")
    public ResponseEntity<PsychologistSlot> createSlot(@RequestParam Integer psychologistId,
                                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
                                                       @RequestParam String startTime,
                                                       @RequestParam String endTime) {

        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        log.info("Parsed request - psychologistId: {}, date: {}, startTime: {}, endTime: {}", psychologistId, date, start, end);
        return ResponseEntity.ok(slotService.createSlot(psychologistId, date, start, end));
    }


    @GetMapping("/available")
    public ResponseEntity<List<PsychologistSlot>> getAvailableSlots() {
        return ResponseEntity.ok(slotService.getAvailableSlots());
    }

    @PostMapping("/book")
    public ResponseEntity<PsychologistSlot> bookSlot(@RequestParam Integer slotId) {
        return ResponseEntity.ok(slotService.bookSlot(slotId));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSlot(@RequestParam Integer psychologistId, @RequestParam Integer psychologistSlotId) {
        boolean deleted = slotService.deleteSlot(psychologistId, psychologistSlotId);
        if (deleted) {
            return ResponseEntity.ok("Slot deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Slot not found or cannot be deleted.");
        }
    }
}

