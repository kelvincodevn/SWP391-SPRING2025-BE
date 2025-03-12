package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
}

