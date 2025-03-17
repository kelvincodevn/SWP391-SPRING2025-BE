package com.example.demo.api.Psychologist;

import com.example.demo.DTO.BookingRequest;
import com.example.demo.DTO.BookingResponse;
import com.example.demo.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/psychologist/bookings")
@SecurityRequirement(name = "api")

public class BookingAPI {
    private final BookingService bookingService;

    public BookingAPI(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{bookingId}/accept")
    public ResponseEntity<String> acceptBooking(@PathVariable Integer bookingId) {
        bookingService.acceptBooking(bookingId);
        return ResponseEntity.ok("Booking accepted. Confirmation email sent to user.");
    }

    @PostMapping("/{bookingId}/decline")
    public ResponseEntity<String> declineBooking(@PathVariable Integer bookingId) {
        bookingService.declineBooking(bookingId);
        return ResponseEntity.ok("Booking declined. Email notification sent, slot reset.");
    }

    @GetMapping("/{bookingId}/confirm")
    public ResponseEntity<String> confirmBooking(@PathVariable Integer bookingId) {
        bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok("Booking confirmed successfully.");
    }

    @PostMapping(value = "/send-report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> sendMedicalReport(
            @RequestParam("bookingId") Integer bookingId,
            @RequestParam("file") MultipartFile file) {

        bookingService.sendMedicalReport(bookingId, file);

        return ResponseEntity.ok("Medical report sent successfully.");
    }

}

