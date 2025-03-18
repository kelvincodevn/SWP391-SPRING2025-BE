package com.example.demo.service;

import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.PsychologistSlotRepository;
import com.example.demo.Repository.SlotRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Booking;
import com.example.demo.entity.PsychologistSlot;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SlotService {
//    private final SlotRepository slotRepository;
//    private final PsychologistRepository psychologistRepository;
//    private final PsychologistSlotRepository psychologistSlotRepository;
//
//    public SlotService(SlotRepository slotRepository, PsychologistRepository psychologistRepository, PsychologistSlotRepository psychologistSlotRepository) {
//        this.slotRepository = slotRepository;
//        this.psychologistRepository = psychologistRepository;
//        this.psychologistSlotRepository = psychologistSlotRepository;
//    }

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    PsychologistSlotRepository psychologistSlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    public PsychologistSlot createSlot(Long psychologistId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        User psychologist = userRepository.findById(psychologistId)
                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        Duration duration = Duration.between(startTime, endTime);
        if (duration.toMinutes() > 120) {
            throw new IllegalArgumentException("Slot duration cannot exceed 2 hours");
        }

        Slot slot = Slot.builder()
                .availableDate(date)
//                .psychologistSlot(psychologistSlot)
                .startTime(startTime)
                .endTime(endTime)
                .available(true)
                .build();
        slot = slotRepository.save(slot);

        PsychologistSlot psychologistSlot = PsychologistSlot.builder()
                .psychologist(psychologist)
                .slot(slot)
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .build();
        return psychologistSlotRepository.save(psychologistSlot);
    }

    public List<PsychologistSlot> getPsychologistSlots(Long psychologistId) {
        return psychologistSlotRepository.findAll().stream()
                .filter(slot -> slot.getPsychologist().getUserID().equals(psychologistId))
                .collect(Collectors.toList());
    }

    public void updateSlot(Long psychologistId, Integer psychologistSlotId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        PsychologistSlot slot = psychologistSlotRepository.findById(psychologistSlotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        Duration duration = Duration.between(startTime, endTime);

        if (duration.toMinutes() > 120) {
            throw new IllegalArgumentException("Slot duration cannot exceed 2 hours");
        }

        if (!slot.getPsychologist().getUserID().equals(psychologistId)) {
            throw new RuntimeException("Unauthorized");
        }
        if (slot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Cannot update a booked slot");
        }
        Slot slotEntity = slot.getSlot();
        slotEntity.setAvailableDate(date);
        slotEntity.setStartTime(startTime);
        slotEntity.setEndTime(endTime);
        slotRepository.save(slotEntity);
    }

    public ResponseEntity<String> deleteSlot(Long psychologistId, Integer psychologistSlotId) {
        PsychologistSlot slot = psychologistSlotRepository.findById(psychologistSlotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        if (!slot.getPsychologist().getUserID().equals(psychologistId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        }
        if (slot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete a booked slot");
        }

        // Kiểm tra booking liên quan đến Slot
        List<Booking> associatedBookings = bookingRepository.findBySlotSlotId(slot.getSlot().getSlotId());
        if (!associatedBookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot delete slot because it has " + associatedBookings.size() + " associated booking(s).");
        }
        // Nếu không có booking, tiến hành xóa
        try {
            psychologistSlotRepository.delete(slot);
            slotRepository.delete(slot.getSlot());
            return ResponseEntity.ok("Slot deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete slot");
        }
    }

    public List<PsychologistSlot> getAvailableSlots(Long psychologistId) {
        return psychologistSlotRepository.findByPsychologistUserIDAndAvailabilityStatus(psychologistId, "AVAILABLE");
    }

}

