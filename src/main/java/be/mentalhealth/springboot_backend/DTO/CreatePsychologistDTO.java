package be.mentalhealth.springboot_backend.DTO;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreatePsychologistDTO {
    @NotBlank
    private String userName;

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;
}
