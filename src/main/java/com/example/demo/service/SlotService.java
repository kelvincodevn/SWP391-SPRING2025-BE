package com.example.demo.service;

import com.example.demo.Repository.PsychologistRepository;
import com.example.demo.Repository.PsychologistSlotRepository;
import com.example.demo.Repository.SlotRepository;
import com.example.demo.entity.Psychologist;
import com.example.demo.entity.PsychologistSlot;
import com.example.demo.entity.Slot;

import com.example.demo.enums.AvailabilityStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SlotService {
    private final SlotRepository slotRepository;
    private final PsychologistRepository psychologistRepository;
    private final PsychologistSlotRepository psychologistSlotRepository;

    public SlotService(SlotRepository slotRepository, PsychologistRepository psychologistRepository, PsychologistSlotRepository psychologistSlotRepository) {
        this.slotRepository = slotRepository;
        this.psychologistRepository = psychologistRepository;
        this.psychologistSlotRepository = psychologistSlotRepository;
    }

    public PsychologistSlot createSlot(Integer psychologistId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Psychologist psychologist = psychologistRepository.findById(psychologistId)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        Slot slot = Slot.builder()
                .availableDate(date)
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

    public List<PsychologistSlot> getAvailableSlots() {
        return psychologistSlotRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE);
    }

    public PsychologistSlot bookSlot(Integer slotId) {
        PsychologistSlot slot = psychologistSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Slot is not available");
        }

        slot.setAvailabilityStatus(AvailabilityStatus.BOOKED);
        return psychologistSlotRepository.save(slot);
    }
    public boolean deleteSlot(Integer psychologistId, Integer psychologistSlotId) {
        Optional<PsychologistSlot> slotOpt = psychologistSlotRepository.findById(psychologistSlotId);
        if (slotOpt.isPresent()) {
            PsychologistSlot slot = slotOpt.get();

            // Kiểm tra psychologist có quyền xóa không
            if (!slot.getPsychologist().getPsychoId().equals(psychologistId)) {
                return false;
            }

            // Chỉ xóa nếu slot đang trống (AVAILABLE)
            if (slot.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE) {
                psychologistSlotRepository.delete(slot);
                return true;
            }
        }
        return false;
    }

}

