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

<<<<<<< HEAD
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

=======
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;
}
