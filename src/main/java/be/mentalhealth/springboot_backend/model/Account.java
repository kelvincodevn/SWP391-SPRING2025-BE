package be.mentalhealth.springboot_backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

//generate getter và setter
@Data
public class Account {

    //id thường tự generate sau khi tự và lưu dưới DB
    public long id;
    @NotBlank(message = "Fullname cannot be blank!")
    String fullName;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email!")
    String email;
    @Pattern(regexp = "/(84|0[3|5|7|8|9])+([0-9]{8})\\b/g", message = "Invalid phone")
    String phone;
    @NotBlank(message = "Password cannot be blank!")
    String password;

}
