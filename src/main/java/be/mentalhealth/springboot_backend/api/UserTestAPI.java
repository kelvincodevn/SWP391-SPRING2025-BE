package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.DTO.UserAnswersRequestDTO;
import be.mentalhealth.springboot_backend.Repository.*;
import be.mentalhealth.springboot_backend.entity.*;
import be.mentalhealth.springboot_backend.service.AuthenticationService;
import be.mentalhealth.springboot_backend.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/User/api/usertests")
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

    @Transactional
    @PostMapping("/submit-answers")
    public ResponseEntity<?> submitUserTest(@RequestBody UserAnswersRequestDTO request, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
        }

        // Call the service method to handle test submission
        return testService.submitTest(request, user);
    }


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
    public ResponseEntity<List<TestResult>> getUserTestResults(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = userDetails.getUsername(); // Get the logged-in user's username
        List<TestResult> results = testResultRepository.findByUserUsername(username); // Fetch only user's tests

        return ResponseEntity.ok(results);
    }

}
