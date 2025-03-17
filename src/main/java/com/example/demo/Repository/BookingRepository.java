package com.example.demo.Repository;

import com.example.demo.entity.Booking;
import com.example.demo.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByStatus(BookingStatus status);
}


