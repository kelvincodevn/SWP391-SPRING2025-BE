package com.example.demo.api.Psychologist;

import com.example.demo.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/psychologist/bookings")
@SecurityRequirement(name = "api")
public class PsychologistBookingAPI {

    @Autowired
    BookingService bookingService;

    @PostMapping("/{bookingId}/accept")
    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
    public ResponseEntity<String> acceptBooking(@PathVariable Integer bookingId) {
        bookingService.acceptBooking(bookingId);
        return ResponseEntity.ok("Booking accepted");
    }

    @PostMapping("/{bookingId}/decline")
    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
    public ResponseEntity<String> declineBooking(@PathVariable Integer bookingId) {
        bookingService.declineBooking(bookingId);
        return ResponseEntity.ok("Booking declined");
    }

    @PostMapping(value = "/complete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
    public ResponseEntity<String> completeBooking(@RequestParam Integer bookingId, @RequestParam("file") MultipartFile file) {
        bookingService.completeBooking(bookingId, file);
        return ResponseEntity.ok("Booking completed and report sent");
    }
}
