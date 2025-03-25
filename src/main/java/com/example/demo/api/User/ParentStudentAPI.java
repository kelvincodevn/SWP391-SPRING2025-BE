package com.example.demo.api.User;

import com.example.demo.entity.ParentStudent;
import com.example.demo.entity.User;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.ParentStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/parent-student")
public class ParentStudentAPI {

    @Autowired
    private ParentStudentService parentStudentService;

    @Autowired
    private AuthenticationService authenticationService;

    // API để phụ huynh tìm kiếm học sinh theo email
    @GetMapping("/search-student")
    public ResponseEntity<?> searchStudentByEmail(@RequestParam String email) {
        try {
            User student = parentStudentService.findStudentByEmail(email);
            return ResponseEntity.ok(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // API để phụ huynh gửi yêu cầu liên kết
    @PostMapping("/send-request")
    public ResponseEntity<?> sendLinkRequest(@RequestParam Long studentId) {
        try {
            Long parentId = authenticationService.getLoggedInUserId();
            ParentStudent parentStudent = parentStudentService.sendLinkRequest(parentId, studentId);
            return ResponseEntity.ok(parentStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // API để học sinh xem danh sách yêu cầu liên kết
    @GetMapping("/link-requests")
    public ResponseEntity<?> getLinkRequests() {
        try {
            Long studentId = authenticationService.getLoggedInUserId();
            List<ParentStudent> requests = parentStudentService.getLinkRequestsForStudent(studentId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // API để học sinh xác nhận hoặc từ chối yêu cầu liên kết
    @PostMapping("/respond-request")
    public ResponseEntity<?> respondToLinkRequest(@RequestParam Long parentId, @RequestParam boolean confirm) {
        try {
            Long studentId = authenticationService.getLoggedInUserId();
            ParentStudent parentStudent = parentStudentService.respondToLinkRequest(parentId, studentId, confirm);
            return ResponseEntity.ok(parentStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // API để phụ huynh xem danh sách học sinh đã liên kết
    @GetMapping("/students")
    public ResponseEntity<?> getStudentsByParent() {
        try {
            Long parentId = authenticationService.getLoggedInUserId();
            List<User> students = parentStudentService.getStudentsByParent(parentId);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/parents")
    public ResponseEntity<?> getParentsByStudent() {
        try {
            Long studentId = authenticationService.getLoggedInUserId();
            List<User> parents = parentStudentService.getParentsByStudent(studentId);
            return ResponseEntity.ok(parents);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/cancel-link")
    public ResponseEntity<?> cancelLink(@RequestParam Long parentId) {
        try {
            Long studentId = authenticationService.getLoggedInUserId();
            ParentStudent parentStudent = parentStudentService.cancelLink(parentId, studentId);
            return ResponseEntity.ok(parentStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/cancel-link-by-parent")
    public ResponseEntity<?> cancelLinkByParent(@RequestParam Long studentId) {
        try {
            Long parentId = authenticationService.getLoggedInUserId(); // Lấy parentId từ thông tin người dùng đăng nhập
            ParentStudent parentStudent = parentStudentService.cancelLink(parentId, studentId);
            return ResponseEntity.ok(parentStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}