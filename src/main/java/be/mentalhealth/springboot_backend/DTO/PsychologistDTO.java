package be.mentalhealth.springboot_backend.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PsychologistDTO {
    private String userName;
    private String fullName;
    private String password;
    private String email;

    private String phone;
    private LocalDateTime dob;
    private String gender;
    private String avatar;
    private Float serviceFee;

    private PsychologistDetailDTO psychologistDetail;
}

