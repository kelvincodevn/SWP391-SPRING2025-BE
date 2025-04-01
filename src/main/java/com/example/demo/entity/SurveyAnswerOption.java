package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "survey_answer_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_answer_option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_question_id", nullable = false)
    @JsonBackReference
    private SurveyQuestion question;

    private String answerText; // e.g., "Never", "Rarely", ...
}