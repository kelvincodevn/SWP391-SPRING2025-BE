package be.mentalhealth.springboot_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetOfQuestionsDTO {
    private int questionNumber;
    private String questionText;
    private double maxScore;
}

