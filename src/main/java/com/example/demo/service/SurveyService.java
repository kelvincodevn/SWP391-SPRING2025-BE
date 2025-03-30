package com.example.demo.service;

import com.example.demo.DTO.SurveyResponseDTO;
import com.example.demo.Repository.SurveyQuestionRepository;
import com.example.demo.Repository.SurveyRepository;
import com.example.demo.Repository.SurveyResponseRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.*;
import com.example.demo.enums.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final UserRepository userRepository;

    @Transactional
    public Survey createSurveyFromExcel(MultipartFile file, User manager, ScheduleType scheduleType, LocalDateTime startTime, LocalDateTime endTime, String recurrenceInterval) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<SurveyQuestion> questions = new ArrayList<>();
            String surveyTitle = null;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                surveyTitle = getCellValue(row.getCell(0)); // Survey Title
                String questionText = getCellValue(row.getCell(1)); // Question Text
                String options = Arrays.stream(new String[]{
                                getCellValue(row.getCell(2)), // Option A
                                getCellValue(row.getCell(3)), // Option B
                                getCellValue(row.getCell(4)), // Option C
                                getCellValue(row.getCell(5))  // Option D
                        }).filter(opt -> opt != null && !opt.isEmpty())
                        .collect(Collectors.joining(",")); // Lưu options dạng chuỗi

                SurveyQuestion question = new SurveyQuestion();
                question.setQuestionText(questionText);
                question.setOptions(options);
                questions.add(question);
            }

            Survey survey = new Survey();
            survey.setTitle(surveyTitle);
            survey.setDescription("Imported from Excel");
            survey.setScheduleType(scheduleType);
            survey.setStartTime(startTime);
            survey.setEndTime(endTime);
            survey.setRecurrenceInterval(recurrenceInterval);
            survey.setCreatedBy(manager);
            survey.setStatus(SurveyStatus.DRAFT);
            survey.setQuestions(questions);

            questions.forEach(q -> q.setSurvey(survey));
            return surveyRepository.save(survey);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> null;
        };
    }

    public List<Survey> getAllSurveys() {
        try {
            return surveyRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all surveys: " + e.getMessage(), e);
        }
    }

    public List<Survey> getActiveSurveys() {
        try {
            LocalDateTime now = LocalDateTime.now();
            return surveyRepository.findByStatusAndStartTimeBeforeAndEndTimeAfter(
                    SurveyStatus.ACTIVE, now, now);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch active surveys: " + e.getMessage(), e);
        }
    }

    @Transactional
    public SurveyResponse submitSurveyResponse(SurveyResponseDTO request, User user) {
        try {
            Survey survey = surveyRepository.findById(request.getSurveyId())
                    .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + request.getSurveyId()));
            if (survey.getStatus() != SurveyStatus.ACTIVE) {
                throw new IllegalStateException("Survey is not active");
            }

            SurveyResponse response = new SurveyResponse();
            response.setSurvey(survey);
            response.setUser(user);

            List<SurveyAnswer> answers = request.getAnswers().stream().map(a -> {
                try {
                    SurveyAnswer answer = new SurveyAnswer();
                    answer.setResponse(response);
                    answer.setQuestion(surveyQuestionRepository.findById(a.getQuestionId())
                            .orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + a.getQuestionId())));
                    answer.setAnswerText(a.getAnswerText());
                    return answer;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to process answer for question ID " + a.getQuestionId() + ": " + e.getMessage(), e);
                }
            }).collect(Collectors.toList());

            response.setAnswers(answers);
            return surveyResponseRepository.save(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to submit survey response: " + e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Chạy hàng ngày lúc nửa đêm
    public void activateRecurringSurveys() {
        try {
            List<Survey> recurringSurveys = surveyRepository.findAll().stream()
                    .filter(s -> s.getScheduleType() == ScheduleType.RECURRING)
                    .collect(Collectors.toList());

            LocalDateTime now = LocalDateTime.now();
            for (Survey survey : recurringSurveys) {
                if (shouldActivateSurvey(survey, now)) {
                    survey.setStatus(SurveyStatus.ACTIVE);
                    survey.setStartTime(now);
                    survey.setEndTime(calculateEndTime(survey, now));
                    surveyRepository.save(survey);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to activate recurring surveys: " + e.getMessage(), e);
        }
    }

    private boolean shouldActivateSurvey(Survey survey, LocalDateTime now) {
        return survey.getStartTime().isBefore(now) && survey.getStatus() == SurveyStatus.DRAFT;
    }

    private LocalDateTime calculateEndTime(Survey survey, LocalDateTime start) {
        switch (survey.getRecurrenceInterval()) {
            case "MONTHLY": return start.plusMonths(1);
            case "WEEKLY": return start.plusWeeks(1);
            default: return start.plusDays(1);
        }
    }
}