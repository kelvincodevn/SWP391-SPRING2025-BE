package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.PsychologistSlot;
<<<<<<< HEAD
import be.mentalhealth.springboot_backend.entity.Slot;
=======
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
import be.mentalhealth.springboot_backend.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c

@Repository
public interface PsychologistSlotRepository extends JpaRepository<PsychologistSlot, Integer> {
    List<PsychologistSlot> findByAvailabilityStatus(AvailabilityStatus status);
<<<<<<< HEAD

    Optional<PsychologistSlot> findBySlot(Slot slot);
}
=======
}

>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
