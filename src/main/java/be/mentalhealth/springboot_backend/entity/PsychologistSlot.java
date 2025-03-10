package be.mentalhealth.springboot_backend.entity;

import be.mentalhealth.springboot_backend.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PsychologistSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psychologistSlotId;

    @ManyToOne
    @JoinColumn(name = "psycholId", nullable = false)
    private Psychologist psychologist;

    @ManyToOne
    @JoinColumn(name = "slotId", nullable = false)
    private Slot slot;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
}

