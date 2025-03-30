package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "survey_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    private String questionText;

    @Column(columnDefinition = "TEXT") // Lưu options dưới dạng JSON hoặc chuỗi
    private String options; // e.g., "Good,Okay,Bad,Terrible"

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyAnswer> answers;
}