package be.mentalhealth.springboot_backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Account {

    public long id;

    String fullname;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email!")
    String email;
    @Pattern(regexp = "/(84|0[3|5|7|8|9])+([0-9]{8})\\b/g", message = "Invalid phone!")
    String phone;
    @NotBlank(message = "password can not be blank!")
    String password;
}
