package com.example.demo.Repository;

import com.example.demo.entity.Booking;
import com.example.demo.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findBySlotSlotId(Integer slotId);

//    List<Booking> findByUser_UserID(Long userID);

//    List<Booking> findByPsychologistSlot_Psychologist_UserID(Long psychologistId);

//    @Query("SELECT b FROM Booking b WHERE b.slot IN (SELECT ps.slot FROM PsychologistSlot ps WHERE ps.psychologist.userID = :userId)")
//    List<Booking> findByPsychologistSlot_Psychologist_UserID(Long userId);

//    long countByUserUserID(Long userId); // Đổi từ countByUserId thành countByUserUserID
//    long countByUserIdAndStatus(Long userId, BookingStatus status);

//    long countByPsychologistId(Long psychologistId);
//    long countByPsychologistIdAndStatus(Long psychologistId, BookingStatus status);
//    List<Booking> findByPsychologistIdAndStatusNot(Long psychologistId, BookingStatus status);

}


