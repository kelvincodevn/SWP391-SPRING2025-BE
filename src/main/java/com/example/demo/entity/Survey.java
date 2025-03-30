package com.example.demo.entity;

import com.example.demo.enums.SurveyStatus;
import com.example.demo.enums.ScheduleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "surveys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private SurveyStatus status = SurveyStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType = ScheduleType.ONE_TIME;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String recurrenceInterval;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyQuestion> questions;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResponse> responses;
}