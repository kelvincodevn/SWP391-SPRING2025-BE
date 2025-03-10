package be.mentalhealth.springboot_backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PsychologistDetailDTO {
    private String major;
    private String workplace;
    private String degree;
}
