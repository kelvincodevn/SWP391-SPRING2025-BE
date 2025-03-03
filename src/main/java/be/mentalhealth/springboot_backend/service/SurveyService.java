package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.entity.Survey;
import be.mentalhealth.springboot_backend.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }
}

