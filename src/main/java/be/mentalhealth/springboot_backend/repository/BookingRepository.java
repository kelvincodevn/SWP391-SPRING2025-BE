package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Booking;
import be.mentalhealth.springboot_backend.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByStatus(BookingStatus status);
}


