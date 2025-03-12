package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.PsychologistSlot;
import be.mentalhealth.springboot_backend.entity.Slot;
import be.mentalhealth.springboot_backend.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PsychologistSlotRepository extends JpaRepository<PsychologistSlot, Integer> {
    List<PsychologistSlot> findByAvailabilityStatus(AvailabilityStatus status);

    Optional<PsychologistSlot> findBySlot(Slot slot);
}
