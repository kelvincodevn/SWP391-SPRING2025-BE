package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.entity.Survey;
import be.mentalhealth.springboot_backend.entity.SurveyEmailLog;
import be.mentalhealth.springboot_backend.repository.SurveyEmailLogRepository;
import be.mentalhealth.springboot_backend.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SurveyRepository surveyRepository;
    private final SurveyEmailLogRepository surveyEmailLogRepository;

    public EmailService(JavaMailSender mailSender, SurveyRepository surveyRepository, SurveyEmailLogRepository surveyEmailLogRepository) {
        this.mailSender = mailSender;
        this.surveyRepository = surveyRepository;
        this.surveyEmailLogRepository = surveyEmailLogRepository;
    }

    public List<SurveyEmailLog> sendSurveyEmails(String title, String description, List<String> emails, String surveyLink) {
        // Lưu survey vào DB
        Survey survey = new Survey();
        survey.setTitle(title);
        survey.setDescription(description);
        survey.setSurveyLink(surveyLink);
        survey = surveyRepository.save(survey);  // Lưu và lấy ID mới

        List<SurveyEmailLog> emailLogs = new ArrayList<>();

        for (String email : emails) {
            boolean isSent = false;
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("Survey Invitation: " + title);
                message.setText("Please complete the survey (ID: " + survey.getId() + "): " + surveyLink);
                mailSender.send(message);
                isSent = true;
            } catch (MailException e) {
                System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            }

            // Lưu vào bảng survey_email_logs
            SurveyEmailLog log = new SurveyEmailLog(email, survey, isSent);
            emailLogs.add(log);
        }

        surveyEmailLogRepository.saveAll(emailLogs);
        return emailLogs;
    }
}
