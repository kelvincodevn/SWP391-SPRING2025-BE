package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.Repository.SetOfQuestionsRepository;
import be.mentalhealth.springboot_backend.Repository.TestAnswerRepository;
import be.mentalhealth.springboot_backend.Repository.TestsRepository;
import be.mentalhealth.springboot_backend.service.TestService;
import be.mentalhealth.springboot_backend.entity.Tests;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/Manager/api/managertests")
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
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is required.");
        }

        try {
            // Call the service to process the Excel file
            ResponseEntity<String> uploadResponse = testService.uploadExcelFile(file);

            if (!uploadResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.badRequest().body(uploadResponse.getBody());
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


}


