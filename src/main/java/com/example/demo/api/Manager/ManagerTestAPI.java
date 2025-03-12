package com.example.demo.api.Manager;

import com.example.demo.DTO.TestScoringDTO;
import com.example.demo.Repository.SetOfQuestionsRepository;
import com.example.demo.Repository.TestAnswerRepository;
import com.example.demo.Repository.TestsRepository;
import com.example.demo.entity.Tests;
import com.example.demo.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/manager/managertests")
@SecurityRequirement(name = "api")
public class ManagerTestAPI {

    @Autowired
    private TestService testService;
    
    @Autowired
    private TestsRepository testsRepository;
    @Autowired
    private SetOfQuestionsRepository setOfQuestionsRepository;
    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @GetMapping
    public List<Tests> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("{testsId}")
    public ResponseEntity<?> getTestDetails(@PathVariable Long testsId) {
        return testService.getTestDetails(testsId);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createTest(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is required.");
            }

            // Gọi service để xử lý file Excel và tạo bài test
            ResponseEntity<String> uploadResponse = testService.uploadExcelFile(file);
            if (!uploadResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to process Excel file.");
            }

            return ResponseEntity.ok("Test uploaded successfully from Excel!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id) {
        return testService.deleteTest(id);
    }

    // Thêm các endpoint cho quản lý Test Scoring
    @PostMapping("/{testId}/scoring")
    public ResponseEntity<?> createTestScoring(@PathVariable Long testId, @RequestBody TestScoringDTO scoringDTO) {
        return testService.createTestScoring(testId, scoringDTO);
    }

    @PutMapping("/scoring/{scoringId}")
    public ResponseEntity<?> updateTestScoring(@PathVariable Long scoringId, @RequestBody TestScoringDTO scoringDTO) {
        return testService.updateTestScoring(scoringId, scoringDTO);
    }

    @GetMapping("/{testId}/scoring")
    public ResponseEntity<?> getTestScoring(@PathVariable Long testId) {
        return testService.getTestScoring(testId);
    }

    @DeleteMapping("/scoring/{scoringId}")
    public ResponseEntity<?> deleteTestScoring(@PathVariable Long scoringId) {
        return testService.deleteTestScoring(scoringId);
    }
}


