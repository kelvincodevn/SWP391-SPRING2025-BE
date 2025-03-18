package com.example.demo.api.Psychologist;

import com.example.demo.DTO.BookingRequest;
import com.example.demo.DTO.BookingResponse;
import com.example.demo.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{bookingId}/confirm")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<String> confirmBooking(@PathVariable Integer bookingId) {
        bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok("Booking confirmed successfully");
    }

    @PostMapping("/{bookingId}/cancel")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<String> cancelBooking(@RequestParam Long userId, @PathVariable Integer bookingId) {
        bookingService.cancelBooking(userId, bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}


