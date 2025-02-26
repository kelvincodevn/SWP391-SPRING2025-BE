package com.example.demo.service;

import com.example.demo.DTO.TestAnswerDTO;
import com.example.demo.DTO.TestDetailsDTO;
import com.example.demo.Repository.SetOfQuestionsRepository;
import com.example.demo.Repository.TestAnswerRepository;
import com.example.demo.Repository.TestsRepository;
import com.example.demo.entity.SetOfQuestions;
import com.example.demo.entity.TestAnswer;
import com.example.demo.entity.Tests;
import com.example.demo.model.ExcelRow;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
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



    public List<Tests> getAllTests() {
        return testsRepository.findAll();
    }

    public ResponseEntity<?> getTestDetails(Long testsId) {
        Optional<Tests> testOptional = testsRepository.findById(testsId);
        if (testOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Test with ID " + testsId + " not found.");
        }
        Tests tests = testOptional.get();
        List<SetOfQuestions> questions = setOfQuestionsRepository.findByTests(tests);
        List<TestAnswerDTO> answers = testAnswerRepository.findByQuestionIn(questions)
                .stream().map(answer -> new TestAnswerDTO(
                        answer.getQuestion().getQuestionNumber(),
                        answer.getQuestion().getQuestionText(),
                        answer.getAnswer(),
                        answer.getScore()))
                .collect(Collectors.toList());
        TestDetailsDTO testDetails = new TestDetailsDTO(
                tests.getId(), tests.getTestsName(), tests.getTestsDescription(), questions.size(), answers);
        return ResponseEntity.ok(testDetails);
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
                return ResponseEntity.badRequest().body("Excel file is empty");
            }
            rowIterator.next(); // Bỏ qua header

            // Lấy dữ liệu từ Excel
            Map<String, List<ExcelRow>> testDataMap = extractExcelData(rowIterator);

            // Tối ưu truy vấn DB bằng cách preload dữ liệu cần thiết
            processExcelData(testDataMap);

            return ResponseEntity.ok("File processed successfully");

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing Excel file: " + e.getMessage());
        }
    }

    private void processExcelData(Map<String, List<ExcelRow>> testDataMap) {
        Set<String> testNames = testDataMap.keySet();

        // Lấy tất cả bài kiểm tra hiện có từ database
        Map<String, Tests> existingTests = testsRepository.findByTestsNameIn(testNames)
                .stream().collect(Collectors.toMap(Tests::getTestsName, Function.identity()));

        List<Tests> newTests = new ArrayList<>();
        List<SetOfQuestions> questionsToSave = new ArrayList<>();
        List<TestAnswer> answersToSave = new ArrayList<>();

        for (Map.Entry<String, List<ExcelRow>> entry : testDataMap.entrySet()) {
            String testName = entry.getKey();
            Tests test = existingTests.get(testName);

            if (test == null) {
                // Không cần tăng maxTestId nữa vì database sẽ tự động sinh ID mới
                test = new Tests();
                test.setTestsName(testName);
                test.setTestsDescription("Generated test: " + testName);
                newTests.add(test);
                existingTests.put(testName, test);
            }

            List<SetOfQuestions> tempQuestions = new ArrayList<>();
            for (ExcelRow row : entry.getValue()) {
                SetOfQuestions question = new SetOfQuestions();
                question.setTests(test);
                question.setQuestionNumber(row.getQuestionNumber());
                question.setQuestionText(row.getQuestionText());
                question.setMaxScore(row.getMaxScore());
                tempQuestions.add(question);
            }

            // Lưu danh sách câu hỏi trước để đảm bảo ID được tạo
            setOfQuestionsRepository.saveAll(tempQuestions);
            questionsToSave.addAll(tempQuestions);

            // Tạo danh sách câu trả lời từ dữ liệu Excel
            for (int i = 0; i < tempQuestions.size(); i++) {
                ExcelRow row = entry.getValue().get(i);
                SetOfQuestions setOfQuestions = tempQuestions.get(i);
                TestAnswer answer = new TestAnswer();
                answer.setQuestion(setOfQuestions);
                answer.setAnswer(row.getAnswerText()); // Lấy answerText từ Excel
                answer.setScore(row.getMaxScore());
                answersToSave.add(answer);
            }
        }

        // Lưu các bài kiểm tra mới vào database
        if (!newTests.isEmpty()) {
            testsRepository.saveAll(newTests);
        }
        // Lưu câu hỏi và câu trả lời
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
                    getNumericValue(row.getCell(6)), // questionNumber (số thứ tự câu hỏi)
                    getStringValue(row.getCell(1)),  // questionText
                    getStringValue(row.getCell(2)),  // answerText
                    getNumericValue(row.getCell(3)), // maxScore
                    getNumericValue(row.getCell(4)), // questionId
                    getNumericValue(row.getCell(5))  // resultId
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

    public ResponseEntity<String> deleteTest(Long id) {
        if (!testsRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Tests not found with ID: " + id);
        }
        testsRepository.deleteById(id);
        return ResponseEntity.ok("Deleted test with ID: " + id);
    }
}



