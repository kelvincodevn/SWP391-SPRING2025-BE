package com.example.demo.api.Psychologist;

import com.example.demo.DTO.BookingRequest;
import com.example.demo.DTO.BookingResponse;
import com.example.demo.DTO.PsychologistDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@SecurityRequirement(name = "api")
public class UserBookingAPI {

    @Autowired
    BookingService bookingService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<BookingResponse> createBooking(@RequestParam Long userId, @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(userId, request));
    }

    @PostMapping("/{bookingId}/cancel")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<String> cancelBooking(@RequestParam Long userId, @PathVariable Integer bookingId) {
        bookingService.cancelBooking(userId, bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }

    @PostMapping("/{bookingId}/confirm")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<String> confirmBooking(@RequestParam Long userId, @PathVariable Integer bookingId) {
        bookingService.confirmBooking(userId, bookingId);
        return ResponseEntity.ok("Booking confirmed successfully");
    }

    // Thêm API để lấy danh sách booking của user
    //có thể bên psychologist cũng xài
    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<List<BookingResponse>> getUserBookings(@RequestParam Long userId) {
        List<BookingResponse> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    // Thêm API để lấy chi tiết booking
    @GetMapping("/{bookingId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<BookingResponse> getBookingDetails(@PathVariable Integer bookingId) {
        BookingResponse bookingDetails = bookingService.getBookingDetails(bookingId);
        return ResponseEntity.ok(bookingDetails);
    }

    // Thêm API để lấy danh sách booking của psychologist
    @GetMapping("/psychologist")
    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
    public ResponseEntity<List<BookingResponse>> getPsychologistBookings(@RequestParam Long psychologistId) {
        List<BookingResponse> bookings = bookingService.getPsychologistBookings(psychologistId);
        return ResponseEntity.ok(bookings);
    }

//    @PostMapping("/{bookingId}/confirm")
//    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
//    public ResponseEntity<String> confirmBooking(@RequestParam Long userId, @PathVariable Integer bookingId) {
//        bookingService.confirmBooking(userId, bookingId);
//        return ResponseEntity.ok("Booking confirmed successfully");
//    }

    @GetMapping("/recommend-psychologists")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<List<PsychologistDTO>> recommendPsychologists(@RequestParam Long userId) {
        List<PsychologistDTO> psychologists = bookingService.recommendPsychologists(userId);
        return ResponseEntity.ok(psychologists);
    }
}


