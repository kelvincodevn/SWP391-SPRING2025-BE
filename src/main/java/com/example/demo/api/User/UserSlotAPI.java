package com.example.demo.api.User;

import com.example.demo.entity.Slot;
import com.example.demo.service.SlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@SecurityRequirement(name = "api")
public class UserSlotAPI {

    private final SlotService slotService;

    public UserSlotAPI(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<List<Slot>> getAvailableSlots(@RequestParam Long psychologistId) {
        return ResponseEntity.ok(slotService.getAvailableSlots(psychologistId));
    }
}
