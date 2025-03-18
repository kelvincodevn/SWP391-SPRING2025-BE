package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Integer slotId;

    @ManyToOne
    @JoinColumn(name = "psychologist_slot_id")
    private PsychologistSlot psychologistSlot;

    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available = true;

    // Constructor mặc định (no-args)
    public Slot() {
    }

    // Constructor đầy đủ tham số
    public Slot(Integer slotId, PsychologistSlot psychologistSlot, LocalDate availableDate, LocalTime startTime, LocalTime endTime, boolean available) {
        this.slotId = slotId;
        this.psychologistSlot = psychologistSlot;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    // Phương thức tĩnh để trả về SlotBuilder
    public static SlotBuilder builder() {
        return new SlotBuilder();
    }

    // Lớp SlotBuilder
    public static class SlotBuilder {
        private Integer slotId;
        private PsychologistSlot psychologistSlot;
        private LocalDate availableDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private boolean available = true;

        public SlotBuilder() {
        }

        public SlotBuilder slotId(Integer slotId) {
            this.slotId = slotId;
            return this;
        }

        public SlotBuilder psychologistSlot(PsychologistSlot psychologistSlot) {
            this.psychologistSlot = psychologistSlot;
            return this;
        }

        public SlotBuilder availableDate(LocalDate availableDate) {
            this.availableDate = availableDate;
            return this;
        }

        public SlotBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public SlotBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public SlotBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        public Slot build() {
            return new Slot(slotId, psychologistSlot, availableDate, startTime, endTime, available);
        }
    }

    // Getter và Setter thủ công
    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public PsychologistSlot getPsychologistSlot() {
        return psychologistSlot;
    }

    public void setPsychologistSlot(PsychologistSlot psychologistSlot) {
        this.psychologistSlot = psychologistSlot;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}