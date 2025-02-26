package com.example.demo.api;

import com.example.demo.Repository.SetOfQuestionsRepository;
import com.example.demo.Repository.TestAnswerRepository;
import com.example.demo.Repository.TestRepository;
import com.example.demo.Repository.TestResultRepository;
import com.example.demo.entity.SetOfQuestions;
import com.example.demo.entity.TestAnswer;
import com.example.demo.entity.TestResult;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.poi.ss.usermodel.*;
import com.example.demo.entity.Test;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/tests")
@SecurityRequirement(name = "api")
public class TestAPI {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    SetOfQuestionsRepository setOfQuestionsRepository;
    @Autowired
    TestAnswerRepository testAnswerRepository;
    @Autowired
    TestResultRepository testResultRepository;
    // Lấy tất cả dữ liệu
    @GetMapping
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // Lấy 1 bản ghi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found with ID: " + id));
        return ResponseEntity.ok(test);
    }

    // Upload file Excel
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.endsWith(".xlsx") && !filename.endsWith(".xls"))) {
            return ResponseEntity.badRequest().body("Only Excel files (.xlsx, .xls) are allowed!");
        }

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                return ResponseEntity.badRequest().body("Excel file is empty");
            }

            // Lấy thông tin bài test
            Row firstRow = rowIterator.next();
            if (firstRow.getCell(0) == null) {
                return ResponseEntity.badRequest().body("Invalid file format: Missing test name");
            }

            String testName = firstRow.getCell(0).getStringCellValue();
            Test test = new Test(testName, "GAD-7 Test");
            test = testRepository.save(test);

            // Tạo test result trước khi lưu câu trả lời
            TestResult testResult = new TestResult();
            testResult.setTest(test);
            testResult.setTotalScore(0);
            testResult = testResultRepository.save(testResult);

            List<SetOfQuestions> questionsList = new ArrayList<>();
            List<TestAnswer> answersList = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0 || row.getCell(0) == null) continue;

                // Lưu câu hỏi
                SetOfQuestions question = new SetOfQuestions();
                question.setTest(test);
                question.setQuestionText(row.getCell(0).getStringCellValue());
                question.setQuestionNumber(getNumericValue(row.getCell(1)));
                question.setMaxScore(3);
                question = setOfQuestionsRepository.save(question);
                questionsList.add(question);

                // Lưu danh sách câu trả lời
                for (int i = 2; i < row.getLastCellNum(); i += 2) {
                    if (row.getCell(i) == null || row.getCell(i + 1) == null) continue;

                    TestAnswer answer = new TestAnswer();
                    answer.setQuestion(question);
                    answer.setAnswer(row.getCell(i).getStringCellValue());
                    answer.setScore(getNumericValue(row.getCell(i + 1)));
                    answer.setResult(testResult);
                    answersList.add(answer);
                }
            }

            testAnswerRepository.saveAll(answersList);
            return ResponseEntity.ok("File uploaded successfully. " + questionsList.size() + " questions saved.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Excel file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Phương thức giúp lấy giá trị số từ một ô Excel, tránh lỗi kiểu dữ liệu.
     */
    private int getNumericValue(Cell cell) {
        if (cell == null) return 0;

        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return 0; // Hoặc throw lỗi nếu cần
                }
            case FORMULA:
                return (int) cell.getNumericCellValue();
            default:
                return 0;
        }
    }





    // Xóa bản ghi theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id) {
        if (!testRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Test not found with ID: " + id);
        }

        testRepository.deleteById(id);
        return ResponseEntity.ok("Deleted test with ID: " + id);
    }
}
