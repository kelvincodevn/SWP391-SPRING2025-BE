package com.example.demo.api.Psychologist;

import com.example.demo.entity.Booking;
import com.example.demo.entity.TestResult;
import com.example.demo.entity.User;
import com.example.demo.service.PsychologistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/psychologists")
@SecurityRequirement(name = "api")
public class PsychologistAPI {

    @Autowired
    private PsychologistService psychologistService;

    @GetMapping
    public ResponseEntity<List<User>> getAllPsychologists() {
        List<User> psychologists = psychologistService.getAllPsychologists();
        return ResponseEntity.ok(psychologists);
    }

//    @GetMapping("/{psychologistId}/stats")
//    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
//    public ResponseEntity<Map<String, Object>> getPsychologistStats(@PathVariable Long psychologistId) {
//        Map<String, Object> stats = psychologistService.getPsychologistStats(psychologistId);
//        return ResponseEntity.ok(stats);
//    }

    // chỉnh cái này
//    @GetMapping("/{psychologistId}/clients")
//    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
//    public ResponseEntity<List<Map<String, Object>>> getPsychologistClients(@PathVariable Long psychologistId) {
//        List<Map<String, Object>> clients = psychologistService.getPsychologistClients(psychologistId);
//        return ResponseEntity.ok(clients);
//    }

//    @GetMapping("/bookings")
//    public List<Booking> getBookingsByPsychologistUserId(@RequestParam Long userId) {
//        return psychologistService.findBookingsByPsychologistUserId(userId);
//    }

//    @GetMapping("/{psychologistId}/profile")
//    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
//    public ResponseEntity<Map<String, Object>> getPsychologistProfile(@PathVariable Long psychologistId) {
//        Map<String, Object> profile = psychologistService.getPsychologistProfile(psychologistId);
//        return ResponseEntity.ok(profile);
//    }

//    @PutMapping("/{psychologistId}/profile")
//    @PreAuthorize("hasAuthority('PSYCHOLOGIST')")
//    public ResponseEntity<Map<String, Object>> updatePsychologistProfile(
//            @PathVariable Long psychologistId,
//            @RequestBody Map<String, Object> profileData) {
//        Map<String, Object> updatedProfile = psychologistService.updatePsychologistProfile(psychologistId, profileData);
//        return ResponseEntity.ok(updatedProfile);
//    }

//    @GetMapping("/booking-count")
//    public long getBookingCount(@RequestParam Long userId) {
//        return psychologistService.countBookingsByUser(userId);
//    }

    // Lấy danh sách bài test của khách hàng
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TestResult>> getTestResultsByUserId(@PathVariable Long userId) {
        List<TestResult> results = psychologistService.getTestResultsByUserId(userId);
        return ResponseEntity.ok(results);
    }

    // Lấy chi tiết kết quả bài test
    @GetMapping("/test-results/{resultId}")
    public ResponseEntity<TestResult> getTestResultById(@PathVariable Long resultId) {
        TestResult result = psychologistService.getTestResultById(resultId);
        return ResponseEntity.ok(result);
    }
}
