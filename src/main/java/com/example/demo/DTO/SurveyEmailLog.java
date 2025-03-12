package com.example.demo.DTO;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "survey_email_logs")
public class SurveyEmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    private boolean isSent;
    private LocalDateTime sentAt;

    public SurveyEmailLog(String email, Survey survey, boolean isSent) {
        this.email = email;
        this.survey = survey;
        this.isSent = isSent;
        this.sentAt = LocalDateTime.now();
    }
}

