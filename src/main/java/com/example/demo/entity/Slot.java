package com.example.demo.entity;

import com.example.demo.enums.AvailabilityStatus;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Liên kết trực tiếp với User (psychologist)

    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus; // Trạng thái khả dụng (AVAILABLE, BOOKED)

    // Constructor mặc định
    public Slot() {
    }

    // Constructor đầy đủ tham số
    public Slot(Integer slotId, User user, LocalDate availableDate, LocalTime startTime, LocalTime endTime, AvailabilityStatus availabilityStatus) {
        this.slotId = slotId;
        this.user = user;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availabilityStatus = availabilityStatus;
    }

    // Phương thức tĩnh để trả về SlotBuilder
    public static SlotBuilder builder() {
        return new SlotBuilder();
    }

    // Lớp SlotBuilder
    public static class SlotBuilder {
        private Integer slotId;
        private User user;
        private LocalDate availableDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private AvailabilityStatus availabilityStatus;

        public SlotBuilder() {
        }

        public SlotBuilder slotId(Integer slotId) {
            this.slotId = slotId;
            return this;
        }

        public SlotBuilder user(User user) {
            this.user = user;
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

        public SlotBuilder availabilityStatus(AvailabilityStatus availabilityStatus) {
            this.availabilityStatus = availabilityStatus;
            return this;
        }

        public Slot build() {
            return new Slot(slotId, user, availableDate, startTime, endTime, availabilityStatus);
        }
    }

    // Getter và Setter
    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}