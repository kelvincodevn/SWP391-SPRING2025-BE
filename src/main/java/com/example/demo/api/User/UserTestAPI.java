package com.example.demo.api.User;

import com.example.demo.DTO.UserAnswersRequestDTO;
import com.example.demo.Repository.*;
import com.example.demo.entity.*;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/usertests")
@SecurityRequirement(name = "api")
public class UserTestAPI {

    @Autowired
    private TestService testService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private TestsRepository testsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestHistoryRepository testHistoryRepository;
    @Autowired
    private SetOfQuestionsRepository setOfQuestionsRepository;
    @Autowired
    private TestAnswerRepository testAnswerRepository;
    @Autowired
    private TestResultRepository testResultRepository;


     @GetMapping
    public List<Tests> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{testId}")
    public ResponseEntity<?> getUserTestDetails(@PathVariable Long testId) {
        return testService.getTestDetails(testId);
        }

//    @Transactional
//    @PostMapping("/submit-answers")
//    public ResponseEntity<?> submitUserTest(@RequestBody UserAnswersRequestDTO request, @AuthenticationPrincipal User user) {
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
//        }
//
//        // Call the service method to handle test submission and get the result
//        ResponseEntity<Map<String, Object>> response = testService.submitTest(request, user);
//        return response;
//    }

    @Transactional
    @PostMapping("/submit-answers")
    public ResponseEntity<?> submitUserTest(@RequestBody UserAnswersRequestDTO request, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }

        // Gọi phương thức submitTest và trả về kết quả
        ResponseEntity<Map<String, Object>> response = testService.submitTest(request, user);
        return response;
    }

    //    public ResponseEntity<?> submitUserTest(@RequestBody UserAnswersRequestDTO request, @AuthenticationPrincipal User user) {
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
//        }
//
//        // Call the service method to handle test submission
//        return testService.submitTest(request, user);
//    }


    @GetMapping("/history")
    public ResponseEntity<?> getUserTestHistory(@RequestParam Long resultId) {
        Long userId = authenticationService.getLoggedInUserId();

        Optional<TestHistory> historyOptional = testHistoryRepository.findByTestResult_ResultIdAndUser_UserID(resultId, userId);

        if (historyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test history not found for the given result ID.");
        }

        TestHistory history = historyOptional.get();

        // Map TestAnswerDetail entries to DTO format
        List<Map<String, Object>> answerDetails = new ArrayList<>();
        for (TestAnswerDetail detail : history.getAnswers()) {
            Map<String, Object> question = Map.of(
                    "question", detail.getQuestion().getQuestionText(),
                    "answer", detail.getAnswer(),
                    "score", detail.getScore()
            );
            answerDetails.add(question);
        }

        // Prepare response JSON
        Map<String, Object> response = Map.of(
                "testVersion", history.getTestVersion(),
                "answers", answerDetails,
                "totalScore", history.getTestResult().getTotalScore()
        );

        return ResponseEntity.ok(response);
    }



    @GetMapping("/results")
    public ResponseEntity<List<TestResult>> getAllUserTestResults() {
        List<TestResult> results = testResultRepository.findAll();
        return ResponseEntity.ok(results);
    }

    // API sửa đổi để lấy test results của user đang đăng nhập
    @GetMapping("/user-results")
    public ResponseEntity<List<TestResult>> getUserTestResults(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Lấy userId của user đang đăng nhập
        Long userId = user.getUserID();

        // Truy vấn TestResult theo userId
        List<TestResult> results = testResultRepository.findByUser_UserID(userId);
        return ResponseEntity.ok(results);
    }
}
