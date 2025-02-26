package com.example.demo.service;

import com.example.demo.Repository.TestRepository;

import com.example.demo.entity.Test;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    // Lấy tất cả các bản ghi
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // Lấy một bản ghi theo ID
    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found with ID: " + id));
    }

    // Xóa bản ghi theo ID
    public void deleteTest(Long id) {
        if (!testRepository.existsById(id)) {
            throw new RuntimeException("Test not found with ID: " + id);
        }
        testRepository.deleteById(id);
    }

    // Xử lý file Excel và lưu vào database
    public String saveTestsFromExcel(MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty";
        }

        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.endsWith(".xlsx") && !filename.endsWith(".xls"))) {
            return "Only Excel files (.xlsx, .xls) are allowed!";
        }

        List<Test> testList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua dòng tiêu đề

                Test test = new Test();
                test.setTestName(row.getCell(0).getStringCellValue());
                test.setTestDescription(row.getCell(1).getStringCellValue());

                testList.add(test);
            }

            testRepository.saveAll(testList);
            return "File uploaded successfully. " + testList.size() + " records saved.";

        } catch (IOException e) {
            return "Error processing Excel file: " + e.getMessage();
        }
    }
}
