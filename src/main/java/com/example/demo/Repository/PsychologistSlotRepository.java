package com.example.demo.Repository;

import com.example.demo.entity.PsychologistSlot;
import com.example.demo.entity.Slot;
import com.example.demo.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PsychologistSlotRepository extends JpaRepository<PsychologistSlot, Integer> {
    List<PsychologistSlot> findByAvailabilityStatus(AvailabilityStatus status);


    Optional<PsychologistSlot> findBySlot(Slot slot);

    List<PsychologistSlot> findByPsychologistUserIDAndAvailabilityStatus(Long userID, String availabilityStatus);

    long countByPsychologistUserID(Long psychologistId); // Đổi từ countByPsychologistId thành countByPsychologistUserID
}
