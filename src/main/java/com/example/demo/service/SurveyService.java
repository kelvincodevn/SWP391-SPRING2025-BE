package com.example.demo.service;

import com.example.demo.DTO.SurveyResponseDTO;
import com.example.demo.Repository.*;
import com.example.demo.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final SurveyAnswerOptionRepository surveyAnswerOptionRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final UserRepository userRepository;

    @Transactional
    public Survey createSurveyFromExcel(MultipartFile file, User manager) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<SurveyQuestion> questions = new ArrayList<>();
            String surveyName = null;

            int questionNumber = 1;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                surveyName = getCellValue(row.getCell(0)); // Survey Name
                if (surveyName == null || surveyName.isEmpty()) {
                    throw new IllegalArgumentException("Survey Name is required");
                }

                String questionText = getCellValue(row.getCell(1)); // Question Text
                if (questionText == null || questionText.isEmpty()) {
                    throw new IllegalArgumentException("Question Text is required in row " + (row.getRowNum() + 1));
                }

                List<String> options = List.of(
                        getCellValue(row.getCell(2)), // Option A
                        getCellValue(row.getCell(3)), // Option B
                        getCellValue(row.getCell(4)), // Option C
                        getCellValue(row.getCell(5))  // Option D
                ).stream().filter(opt -> opt != null && !opt.isEmpty()).collect(Collectors.toList());

                if (options.isEmpty()) {
                    throw new IllegalArgumentException("At least one option is required for question in row " + (row.getRowNum() + 1));
                }

                SurveyQuestion question = new SurveyQuestion();
                question.setQuestionNumber(questionNumber++);
                question.setQuestionText(questionText);
                List<SurveyAnswerOption> answerOptions = options.stream().map(opt -> {
                    SurveyAnswerOption answerOption = new SurveyAnswerOption();
                    answerOption.setAnswerText(opt);
                    answerOption.setQuestion(question);
                    return answerOption;
                }).collect(Collectors.toList());
                question.setAnswerOptions(answerOptions);
                questions.add(question);
            }

            if (questions.isEmpty()) {
                throw new IllegalArgumentException("Excel file must contain at least one question");
            }

            Survey survey = new Survey();
            survey.setSurveyName(surveyName);
            survey.setSurveyDescription("Imported from Excel");
            survey.setCreatedBy(manager);
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
        return surveyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Survey getSurveyDetails(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + surveyId));
    }

    @Transactional
    public SurveyResponse submitSurveyResponse(SurveyResponseDTO request, User user) {
        Survey survey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with ID: " + request.getSurveyId()));

        SurveyResponse response = new SurveyResponse();
        response.setSurvey(survey);
        response.setUser(user);
        response.setSubmittedAt(LocalDateTime.now());

        List<SurveyAnswer> answers = request.getAnswers().stream().map(a -> {
            SurveyAnswer answer = new SurveyAnswer();
            answer.setResponse(response);
            // Chuyển đổi Integer thành Long
            answer.setQuestion(surveyQuestionRepository.findById(Long.valueOf(a.getQuestionId()))
                    .orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + a.getQuestionId())));
            answer.setAnswerText(a.getAnswerText());
            return answer;
        }).collect(Collectors.toList());

        response.setAnswers(answers);
        return surveyResponseRepository.save(response);
    }

    public List<SurveyResponse> getUserSurveyHistory(User user) {
        return surveyResponseRepository.findByUser(user);
    }

    public List<SurveyResponse> getAllSurveyHistory() {
        return surveyResponseRepository.findAll();
    }

    public List<SurveyResponse> getSurveyHistoryByUser(Long userId) {
        return surveyResponseRepository.findByUser_UserID(userId);
    }

    public SurveyResponse getSurveyResponseDetails(Long responseId) {
        return surveyResponseRepository.findById(responseId)
                .orElseThrow(() -> new IllegalArgumentException("Survey response not found with ID: " + responseId));
    }
}