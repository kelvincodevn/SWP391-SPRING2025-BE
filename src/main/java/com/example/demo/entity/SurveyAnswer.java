package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "survey_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_answer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_response_id", nullable = false)
    @JsonBackReference
    private SurveyResponse response;

    @ManyToOne
    @JoinColumn(name = "survey_question_id", nullable = false)
    private SurveyQuestion question;

    private String answerText; // Câu trả lời student chọn, e.g., "Never"
}