package com.example.demo.Repository;

import com.example.demo.entity.Slot;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {

    List<Slot> findByUser(User user);

    List<Slot> findByUserUserIDAndAvailableDate(Long userId, LocalDate availableDate);

    List<Slot> findBySlotIdIn(List<Integer> slotIds);
}

