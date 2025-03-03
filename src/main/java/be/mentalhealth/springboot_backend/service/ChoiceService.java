package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.entity.Choice;
import be.mentalhealth.springboot_backend.entity.Question;
import be.mentalhealth.springboot_backend.repository.ChoiceRepository;
import be.mentalhealth.springboot_backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class ChoiceService {
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    public ChoiceService(ChoiceRepository choiceRepository, QuestionRepository questionRepository) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
    }

    public Choice addChoice(Long questionId, Choice choice) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        choice.setQuestion(question);
        return choiceRepository.save(choice);
    }

    public void deleteChoice(Long id) {
        choiceRepository.deleteById(id);
    }
}
