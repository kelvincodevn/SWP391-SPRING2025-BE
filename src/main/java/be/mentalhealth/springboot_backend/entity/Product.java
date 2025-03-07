package be.mentalhealth.springboot_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotBlank
    public String name;

    @Min(value = 0)
    public float price;
    @Min(value = 0)
    public int quality;
    @NotBlank
    public String image;

    @Pattern(regexp = "PO\\d{5}", message = "code must be POxxxxx!")
    @Column(unique = true)
    public String code;

    public boolean isDeleted = false;
}
