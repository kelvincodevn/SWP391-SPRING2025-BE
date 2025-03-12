package be.mentalhealth.springboot_backend.entity;

import be.mentalhealth.springboot_backend.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

@Builder
public class PsychologistSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psychologistSlotId;

    public Integer getPsychologistSlotId() {
        return psychologistSlotId;
    }

    public void setPsychologistSlotId(Integer psychologistSlotId) {
        this.psychologistSlotId = psychologistSlotId;
    }

    public Psychologist getPsychologist() {
        return psychologist;
    }

    public void setPsychologist(Psychologist psychologist) {
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

    @ManyToOne
    @JoinColumn(name = "psycholId", nullable = false)
    private Psychologist psychologist;

    @ManyToOne
    @JoinColumn(name = "slotId", nullable = false)
    private Slot slot;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    public PsychologistSlot() {
    }

    public PsychologistSlot(Integer psychologistSlotId, Psychologist psychologist, Slot slot, AvailabilityStatus availabilityStatus) {
        this.psychologistSlotId = psychologistSlotId;
        this.psychologist = psychologist;
        this.slot = slot;
        this.availabilityStatus = availabilityStatus;
    }

    public static PsychologistSlotBuilder builder() {
        return new PsychologistSlotBuilder();
    }

    public static class PsychologistSlotBuilder {
        private Integer psychologistSlotId;
        private Psychologist psychologist;
        private Slot slot;
        private AvailabilityStatus availabilityStatus;

        public PsychologistSlotBuilder psychologistSlotId(Integer psychologistSlotId) {
            this.psychologistSlotId = psychologistSlotId;
            return this;
        }

        public PsychologistSlotBuilder psychologist(Psychologist psychologist) {
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
}

