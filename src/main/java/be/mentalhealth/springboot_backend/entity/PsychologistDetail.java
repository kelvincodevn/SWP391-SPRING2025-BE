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

    public Integer getPsychologistDetailId() {
        return psychologistDetailId;
    }

    public void setPsychologistDetailId(Integer psychologistDetailId) {
        this.psychologistDetailId = psychologistDetailId;
    }

    public Psychologist getPsychologist() {
        return psychologist;
    }

    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    private String major;
    private String workplace;
    private String degree;
}
