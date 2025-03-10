package be.mentalhealth.springboot_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "psychologist_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PsychologistDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psychologistDetailId;

    @OneToOne
    @JoinColumn(name = "psychologist_id", referencedColumnName = "psychoId")
    @JsonBackReference
    private Psychologist psychologist;

    private String major;
    private String workplace;
    private String degree;
}
