package com.example.demo.service;

import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.SlotRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Slot;
import com.example.demo.entity.User;
import com.example.demo.enums.AvailabilityStatus;
import com.example.demo.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SlotService {
    @Autowired
    SlotRepository slotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    public Slot createSlot(Long psychologistId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        User psychologist = userRepository.findById(psychologistId)
                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        Duration duration = Duration.between(startTime, endTime);
        if (duration.toMinutes() > 120) {
            throw new IllegalArgumentException("Slot duration cannot exceed 2 hours");
        }

        Slot slot = Slot.builder()
                .user(psychologist)
                .availableDate(date)
                .startTime(startTime)
                .endTime(endTime)
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .build();

        return slotRepository.save(slot);
    }

    public List<Slot> getPsychologistSlots(Long psychologistId) {
        return slotRepository.findAll().stream()
                .filter(slot -> slot.getUser().getUserID().equals(psychologistId))
                .toList();
    }

    public void updateSlot(Long psychologistId, Integer slotId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        Duration duration = Duration.between(startTime, endTime);

        if (duration.toMinutes() > 120) {
            throw new IllegalArgumentException("Slot duration cannot exceed 2 hours");
        }

        if (!slot.getUser().getUserID().equals(psychologistId)) {
            throw new RuntimeException("Unauthorized");
        }
        if (slot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Cannot update a booked slot");
        }

        slot.setAvailableDate(date);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slotRepository.save(slot);
    }

    public ResponseEntity<String> deleteSlot(Long psychologistId, Integer slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        if (!slot.getUser().getUserID().equals(psychologistId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        }
        if (slot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete a booked slot");
        }

        // Kiểm tra booking liên quan đến Slot
        List<Booking> associatedBookings = bookingRepository.findBySlotSlotId(slot.getSlotId());
        if (!associatedBookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot delete slot because it has " + associatedBookings.size() + " associated booking(s).");
        }

        // Xóa Slot
        try {
            slotRepository.delete(slot);
            return ResponseEntity.ok("Slot deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete slot");
        }
    }

    public List<Slot> getAvailableSlots(Long psychologistId) {
        return slotRepository.findAll().stream()
                .filter(slot -> slot.getUser().getUserID().equals(psychologistId) && slot.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE)
                .toList();
    }
}