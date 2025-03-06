package com.example.demo.api.User;


import com.example.demo.DTO.*;
import com.example.demo.Repository.*;
import com.example.demo.entity.*;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/usertests")
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

     @GetMapping
    public List<Tests> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{testId}")
    public ResponseEntity<?> getUserTestDetails(@PathVariable Long testId) {
        return testService.getTestDetails(testId);
        }

    // Submit user answers
    @Transactional
    @PostMapping("/submit")
    public ResponseEntity<?> submitUserTest(TestSubmissionDTO submission, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tests test = testsRepository.findById(submission.getTestId())
                .orElseThrow(() -> new RuntimeException("Test not found"));

        int totalScore = 0;
        List<String> submittedAnswers = new ArrayList<>();
        List<Integer> answerScores = new ArrayList<>();

        for (UserAnswerDTO userAnswer : submission.getAnswers()) {
            SetOfQuestions question = setOfQuestionsRepository.findById(userAnswer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Invalid question ID: " + userAnswer.getQuestionId()));

            TestAnswer correctAnswer = testAnswerRepository.findByQuestionAndAnswer(question, userAnswer.getAnswerText())
                    .orElseThrow(() -> new RuntimeException("Invalid answer for question: " + question.getQuestionText()));

            totalScore += correctAnswer.getScore();
            submittedAnswers.add(userAnswer.getAnswerText());
            answerScores.add(correctAnswer.getScore());
        }

        TestHistory history = new TestHistory();
        history.setUser(user);
        history.setTest(test);
        history.setTotalScore(totalScore);
        history.setSubmittedAnswers(submittedAnswers);
        history.setAnswerScores(answerScores);
        history.setSubmittedAt(LocalDateTime.now());

        testHistoryRepository.save(history);

        return ResponseEntity.ok("Test submitted successfully! Your total score: " + totalScore);
    }

    // Fetch user test history
    @GetMapping("/history")
    public ResponseEntity<?> getUserTestHistory() {
        Long userId = authenticationService.getLoggedInUserId();
        List<TestHistory> historyList = testHistoryRepository.findByUser_UserID(userId);
        return ResponseEntity.ok(historyList);
    }
}
