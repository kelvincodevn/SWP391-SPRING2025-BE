//package com.example.demo.entity;
//
//import com.example.demo.enums.AvailabilityStatus;
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//
//@Builder
//public class PsychologistSlot {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer psychologistSlotId;
//
//    public Integer getPsychologistSlotId() {
//        return psychologistSlotId;
//    }
//
//    public void setPsychologistSlotId(Integer psychologistSlotId) {
//        this.psychologistSlotId = psychologistSlotId;
//    }
//
//    public User getPsychologist() {
//        return psychologist;
//    }
//
//    public void setPsychologist(User psychologist) {
//        this.psychologist = psychologist;
//    }
//
//    public Slot getSlot() {
//        return slot;
//    }
//
//    public void setSlot(Slot slot) {
//        this.slot = slot;
//    }
//
//    public AvailabilityStatus getAvailabilityStatus() {
//        return availabilityStatus;
//    }
//
//    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
//        this.availabilityStatus = availabilityStatus;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User psychologist; // Thay Psychologist bằng User
//
//    @ManyToOne
//    @JoinColumn(name = "slot_id", nullable = false)
//    private Slot slot;
//
//    @Enumerated(EnumType.STRING)
//    private AvailabilityStatus availabilityStatus;
//
//    public PsychologistSlot() {
//    }
//
//    public PsychologistSlot(Integer psychologistSlotId, User psychologist, Slot slot, AvailabilityStatus availabilityStatus) {
//        this.psychologistSlotId = psychologistSlotId;
//        this.psychologist = psychologist;
//        this.slot = slot;
//        this.availabilityStatus = availabilityStatus;
//    }
//
//    public static PsychologistSlotBuilder builder() {
//        return new PsychologistSlotBuilder();
//    }
//
//    public static class PsychologistSlotBuilder {
//        private Integer psychologistSlotId;
//        private User psychologist;
//        private Slot slot;
//        private AvailabilityStatus availabilityStatus;
//
//        public PsychologistSlotBuilder psychologistSlotId(Integer psychologistSlotId) {
//            this.psychologistSlotId = psychologistSlotId;
//            return this;
//        }
//
//        public PsychologistSlotBuilder psychologist(User psychologist) {
//            this.psychologist = psychologist;
//            return this;
//        }
//
//        public PsychologistSlotBuilder slot(Slot slot) {
//            this.slot = slot;
//            return this;
//        }
//
//        public PsychologistSlotBuilder availabilityStatus(AvailabilityStatus availabilityStatus) {
//            this.availabilityStatus = availabilityStatus;
//            return this;
//        }
//
//        public PsychologistSlot build() {
//            return new PsychologistSlot(psychologistSlotId, psychologist, slot, availabilityStatus);
//        }
//    }
//
//}
//

package com.example.demo.entity;

import com.example.demo.enums.AvailabilityStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "psychologist_slot")
public class PsychologistSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psychologist_slot_id")
    private Integer psychologistSlotId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User psychologist;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    // Constructor mặc định
    public PsychologistSlot() {
    }

    // Constructor đầy đủ tham số
    public PsychologistSlot(Integer psychologistSlotId, User psychologist, Slot slot, AvailabilityStatus availabilityStatus) {
        this.psychologistSlotId = psychologistSlotId;
        this.psychologist = psychologist;
        this.slot = slot;
        this.availabilityStatus = availabilityStatus;
    }

    // Phương thức tĩnh để trả về PsychologistSlotBuilder
    public static PsychologistSlotBuilder builder() {
        return new PsychologistSlotBuilder();
    }

    // Lớp PsychologistSlotBuilder
    public static class PsychologistSlotBuilder {
        private Integer psychologistSlotId;
        private User psychologist;
        private Slot slot;
        private AvailabilityStatus availabilityStatus;

        public PsychologistSlotBuilder() {
        }

        public PsychologistSlotBuilder psychologistSlotId(Integer psychologistSlotId) {
            this.psychologistSlotId = psychologistSlotId;
            return this;
        }

        public PsychologistSlotBuilder psychologist(User psychologist) {
            this.psychologist = psychologist;
            return this;
        }

        public PsychologistSlotBuilder slot(Slot slot) {
            this.slot = slot;
            return this;
        }

        public PsychologistSlotBuilder availabilityStatus(AvailabilityStatus availabilityStatus) {
            this.availabilityStatus = availabilityStatus;
            return this;
        }

        public PsychologistSlot build() {
            return new PsychologistSlot(psychologistSlotId, psychologist, slot, availabilityStatus);
        }
    }

    // Getter và Setter
    public Integer getPsychologistSlotId() {
        return psychologistSlotId;
    }

    public void setPsychologistSlotId(Integer psychologistSlotId) {
        this.psychologistSlotId = psychologistSlotId;
    }

    public User getPsychologist() {
        return psychologist;
    }

    public void setPsychologist(User psychologist) {
        this.psychologist = psychologist;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
