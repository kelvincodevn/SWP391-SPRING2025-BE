package be.mentalhealth.springboot_backend.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Student {
    @NotBlank(message = "Id can not be blank!")
    public String id;
    @NotBlank(message = "Name can not blank!")
    public String name;
    @Pattern(regexp = "SE\\d{6}", message = "Student code not match structure!")
    public String studentCode;
    @Min(value = 0, message = "Score must at least 0")
    @Max(value = 10, message = "Score must at most 10")
    public float score;
}
