package com.example.demo.service;

import com.example.demo.DTO.*;
import com.example.demo.Repository.*;
import com.example.demo.entity.*;
import com.example.demo.model.ExcelRow;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private TestsRepository testsRepository;
    @Autowired
    private SetOfQuestionsRepository setOfQuestionsRepository;
    @Autowired
    private TestAnswerRepository testAnswerRepository;
    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestHistoryRepository testHistoryRepository;

    @Autowired
    private TestScoringRepository testScoringRepository; // Autowire repository


    Map<Integer, TestAnswerDTO> uniqueAnswersMap = new LinkedHashMap<>();

    List<TestAnswerDTO> uniqueAnswers = new ArrayList<>(uniqueAnswersMap.values());

    public List<Tests> getAllTests() {
        return testsRepository.findAll();
    }

    public ResponseEntity<?> getTestDetails(Long testsId) {
        Optional<Tests> testOptional = testsRepository.findById(testsId);
        if (testOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Test with ID " + testsId + " not found.");
        }

        Tests test = testOptional.get();
        List<SetOfQuestions> questions = setOfQuestionsRepository.findByTests(test);

        // Process questions and answers
        List<QuestionDTO> questionList = questions.stream()
                .map(q -> new QuestionDTO(
                        q.getQuestionNumber(),  // Use existing question number
                        q.getQuestionText(),
                        testAnswerRepository.findByQuestionIn(Collections.singletonList(q)).stream()
                                .map(a -> new AnswerDTO(a.getAnswer(), a.getScore()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        TestListDTO testDetails = new TestListDTO(
                test.getId(),
                test.getTestsName(),
                test.getTestsDescription(),
                questionList.size(),
                questionList
        );

        return ResponseEntity.ok(testDetails);
    }

    //mục API Test Scoring fd
    public ResponseEntity<?> createTestScoring(Long testId, TestScoringDTO scoringDTO) {
        Optional<Tests> testOptional = testsRepository.findById(testId);
        if (testOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Test not found.");
        }
        Tests test = testOptional.get();

        TestScoring scoring = new TestScoring();
        scoring.setTest(test);
        scoring.setMinScore(scoringDTO.getMinScore());
        scoring.setMaxScore(scoringDTO.getMaxScore());
        scoring.setLevel(scoringDTO.getLevel());
        scoring.setDescription(scoringDTO.getDescription());

        testScoringRepository.save(scoring);
        return ResponseEntity.ok("Test scoring created successfully.");
    }

    public ResponseEntity<?> updateTestScoring(Long scoringId, TestScoringDTO scoringDTO) {
        Optional<TestScoring> scoringOptional = testScoringRepository.findById(scoringId);
        if (scoringOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Test scoring not found.");
        }
        TestScoring scoring = scoringOptional.get();

        scoring.setMinScore(scoringDTO.getMinScore());
        scoring.setMaxScore(scoringDTO.getMaxScore());
        scoring.setLevel(scoringDTO.getLevel());
        scoring.setDescription(scoringDTO.getDescription());

        testScoringRepository.save(scoring);
        return ResponseEntity.ok("Test scoring updated successfully.");
    }

    public ResponseEntity<?> getTestScoring(Long testId) {
        Optional<Tests> testOptional = testsRepository.findById(testId);
        if (testOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Test not found.");
        }
        Tests test = testOptional.get();

        List<TestScoring> scorings = testScoringRepository.findByTest(test);
        return ResponseEntity.ok(scorings);
    }

    public ResponseEntity<?> deleteTestScoring(Long scoringId) {
        if (!testScoringRepository.existsById(scoringId)) {
            return ResponseEntity.badRequest().body("Test scoring not found.");
        }
        testScoringRepository.deleteById(scoringId);
        return ResponseEntity.ok("Test scoring deleted successfully.");
    }
    //mục kết thúc API Test Scoring

@Transactional
public ResponseEntity<Map<String, Object>> submitTest(UserAnswersRequestDTO submission, User user) {
    Optional<Tests> testOptional = testsRepository.findById(submission.getTestId());
    if (testOptional.isEmpty()) {
        return ResponseEntity.badRequest().body(Map.of("error", "Test not found."));
    }
    Tests test = testOptional.get();

    // Tạo và lưu TestResult trước
    TestResult testResult = new TestResult();
    testResult.setUser(user);
    testResult.setTest(test);
    testResult.setCreateAt(LocalDateTime.now());
    testResult.setTotalScore(0); // Khởi tạo total score
    testResult.setDescription("default");
    testResult.setLevel("default");
    testResult = testResultRepository.save(testResult); // Lưu để lấy ID

    int totalScore = 0;
    List<TestHistory> historyList = new ArrayList<>();

    // Tạo một TestHistory cho lần submit này
    TestHistory testHistory = new TestHistory();
    testHistory.setTestResult(testResult);
    testHistory.setUser(user);
    testHistory.setTestVersion("v1.0");
    List<TestAnswerDetail> answerDetails = new ArrayList<>(); // Lưu các câu trả lời chi tiết

    for (UserAnswerDTO answerDTO : submission.getAnswers()) {
        Optional<SetOfQuestions> questionOpt = setOfQuestionsRepository.findById(answerDTO.getQuestionId());
        if (questionOpt.isPresent()) {
            SetOfQuestions question = questionOpt.get();

            // Tạo một TestAnswerDetail cho mỗi câu trả lời
            TestAnswerDetail answerDetail = new TestAnswerDetail();
            answerDetail.setTestHistory(testHistory);
            answerDetail.setQuestionNo(question.getQuestionNumber());
            answerDetail.setQuestion(question);
            answerDetail.setAnswer(answerDTO.getAnswerText());
            answerDetail.setScore(answerDTO.getScore());

            totalScore += answerDTO.getScore();
            answerDetails.add(answerDetail);
        }
    }

    // Liên kết các câu trả lời chi tiết với testHistory và lưu
    testHistory.setAnswers(answerDetails);
    historyList.add(testHistory);

    if (!historyList.isEmpty()) {
        testHistoryRepository.saveAll(historyList);
    }

    // Cập nhật total score sau khi xử lý tất cả câu trả lời
    testResult.setTotalScore(totalScore);

    // Logic đánh giá kết quả dựa trên TestScoring
    List<TestScoring> scorings = testScoringRepository.findByTest(test);
    String level = "Unknown";
    String description = "Result evaluation not available.";

    for (TestScoring scoring : scorings) {
        if (scoring.getMaxScore() == null) {
            if (totalScore >= scoring.getMinScore()) {
                level = scoring.getLevel();
                description = scoring.getDescription();
                break;
            }
        } else {
            if (totalScore >= scoring.getMinScore() && totalScore <= scoring.getMaxScore()) {
                level = scoring.getLevel();
                description = scoring.getDescription();
                break;
            }
        }
    }

    // Cập nhật TestResult với level và description đã đánh giá
    testResult.setLevel(level);
    testResult.setDescription(description);
    testResultRepository.save(testResult);

    // Chuẩn bị dữ liệu trả về cho frontend
    Map<String, Object> response = Map.of(
            "totalScore", totalScore,
            "level", level,
            "description", description,
            "resultId", testResult.getResultId(),
            "testId", test.getId(),
            "testName", test.getTestsName()
    );

    return ResponseEntity.ok(response);
}


     @Transactional
    public ResponseEntity<String> uploadExcelFile(MultipartFile file) {
        if (file.isEmpty() || !isExcelFile(file.getOriginalFilename())) {
            return ResponseEntity.badRequest().body("Invalid file format or empty file.");
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) {
                return ResponseEntity.badRequest().body("Excel file is empty.");
            }
            rowIterator.next(); // Skip header

            Map<String, List<ExcelRow>> testDataMap = extractExcelData(rowIterator);
            Set<String> testNames = testDataMap.keySet();
            List<Tests> existingTests = testsRepository.findByTestsNameIn(testNames);

            Map<String, Tests> activeTests = new HashMap<>();
            Map<String, Tests> deletedTests = new HashMap<>();

            for (Tests test : existingTests) {
                if (test.isDeleted()) {
                    deletedTests.put(test.getTestsName(), test);
                } else {
                    activeTests.put(test.getTestsName(), test);
                }
            }

            testDataMap.keySet().removeAll(activeTests.keySet());
            if (testDataMap.isEmpty()) {
                return ResponseEntity.badRequest().body("All test names already exist and are active.");
            }

            for (String testName : deletedTests.keySet()) {
                Tests deletedTest = deletedTests.get(testName);
                deletedTest.setDeleted(false);
                testsRepository.save(deletedTest);
                testDataMap.remove(testName);
            }

            if (!testDataMap.isEmpty()) {
                processExcelData(testDataMap);
            }

            return ResponseEntity.ok("File processed successfully. Restored deleted tests and added new ones.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing Excel file: " + e.getMessage());
        }
    }


    private void processExcelData(Map<String, List<ExcelRow>> testDataMap) {
        Set<String> testNames = testDataMap.keySet();

        Map<String, Tests> existingTests = testsRepository.findByTestsNameIn(testNames)
                .stream().collect(Collectors.toMap(Tests::getTestsName, Function.identity()));

        List<Tests> newTests = new ArrayList<>();
        List<SetOfQuestions> questionsToSave = new ArrayList<>();
        List<TestAnswer> answersToSave = new ArrayList<>();

        for (Map.Entry<String, List<ExcelRow>> entry : testDataMap.entrySet()) {
            String testName = entry.getKey();
            Tests test = existingTests.get(testName);

            if (test == null) {
                test = new Tests();
                test.setTestsName(testName);
                test.setTestsDescription("Generated test: " + testName);
                test = testsRepository.save(test); // Ensure test is saved before use
                existingTests.put(testName, test);
            }

            Map<String, SetOfQuestions> existingQuestions = setOfQuestionsRepository.findByTests(test)
                    .stream().collect(Collectors.toMap(SetOfQuestions::getQuestionText, Function.identity()));

            List<SetOfQuestions> tempQuestions = new ArrayList<>();
            int questionNumber = 1; // Assign question numbers sequentially

            for (ExcelRow row : entry.getValue()) {
                SetOfQuestions question = existingQuestions.get(row.getQuestionText());
                if (question == null) {
                    question = new SetOfQuestions();
                    question.setTests(test);
                    question.setQuestionNumber(questionNumber++); // Assign new sequential number
                    question.setQuestionText(row.getQuestionText());
                    tempQuestions.add(question);
                    existingQuestions.put(row.getQuestionText(), question);
                }
            }

            setOfQuestionsRepository.saveAll(tempQuestions);
            questionsToSave.addAll(tempQuestions);

            for (ExcelRow row : entry.getValue()) {
                SetOfQuestions question = existingQuestions.get(row.getQuestionText());
                if (question != null) {
                    TestAnswer answer = new TestAnswer();
                    answer.setQuestion(question);
                    answer.setAnswer(row.getAnswerText());
                    answer.setScore(row.getMaxScore());
                    answersToSave.add(answer);
                }
            }
        }

        if (!newTests.isEmpty()) {
            testsRepository.saveAll(newTests);
        }
        if (!questionsToSave.isEmpty()) {
            setOfQuestionsRepository.saveAll(questionsToSave);
        }
        if (!answersToSave.isEmpty()) {
            testAnswerRepository.saveAll(answersToSave);
        }
    }



    private Map<String, List<ExcelRow>> extractExcelData(Iterator<Row> rowIterator) {
        Map<String, List<ExcelRow>> testDataMap = new HashMap<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isEmptyRow(row)) continue;
            ExcelRow excelRow = new ExcelRow(
                    getStringValue(row.getCell(0)),  // testName
                    getStringValue(row.getCell(1)),  // questionText
                    getStringValue(row.getCell(2)),  // answerText
                    getNumericValue(row.getCell(3)) // maxScore
            );
            testDataMap.computeIfAbsent(excelRow.getTestName(), k -> new ArrayList<>()).add(excelRow);
        }
        return testDataMap;
    }

    private boolean isExcelFile(String filename) {
        return filename != null && (filename.endsWith(".xlsx") || filename.endsWith(".xls"));
    }

    private boolean isEmptyRow(Row row) {
        if (row == null) return true;
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private String getStringValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> "";
        };
    }

    private int getNumericValue(Cell cell) {
        if (cell == null) return 0;
        return switch (cell.getCellType()) {
            case NUMERIC -> (int) cell.getNumericCellValue();
            case STRING -> {
                try { yield Integer.parseInt(cell.getStringCellValue().trim()); }
                catch (NumberFormatException e) { yield 0; }
            }
            default -> 0;
        };
    }

    @Transactional
    public ResponseEntity<String> deleteTest(Long id) {
        if (!testsRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Test not found with ID: " + id);
        }

        // Retrieve the test and mark it as deleted
        Tests test = testsRepository.findById(id).orElse(null);
        if (test != null) {
            test.setDeleted(true);
            testsRepository.save(test);
        }

        return ResponseEntity.ok("Marked test as deleted with ID: " + id);
    }

}


