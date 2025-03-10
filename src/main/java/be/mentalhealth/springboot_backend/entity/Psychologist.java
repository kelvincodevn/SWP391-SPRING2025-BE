package be.mentalhealth.springboot_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "psychologist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Psychologist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psychoId;

    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private LocalDateTime dob;
    private String gender;
    private String avatar;
    private Float serviceFee;
    private Boolean status;

    @OneToOne(mappedBy = "psychologist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private PsychologistDetail psychologistDetail;

    public void softDelete() {
        this.status = false;
    }
}
