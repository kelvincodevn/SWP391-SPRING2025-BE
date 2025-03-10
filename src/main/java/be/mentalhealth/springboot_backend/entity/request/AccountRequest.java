package be.mentalhealth.springboot_backend.entity.request;

import be.mentalhealth.springboot_backend.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private RoleEnum roleEnum; // Mặc định nếu không truyền vào, sẽ là STUDENT
}
