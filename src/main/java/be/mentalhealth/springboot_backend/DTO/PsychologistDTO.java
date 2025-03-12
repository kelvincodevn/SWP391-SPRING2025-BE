package be.mentalhealth.springboot_backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PsychologistDTO {
    private String userName;
    private String fullName;
    private String password;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Float getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Float serviceFee) {
        this.serviceFee = serviceFee;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public PsychologistDetailDTO getPsychologistDetail() {
        return psychologistDetail;
    }

    public void setPsychologistDetail(PsychologistDetailDTO psychologistDetail) {
        this.psychologistDetail = psychologistDetail;
    }

    private String email;

    private String phone;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String gender;
    private String avatar;
    private Float serviceFee;
    private LocalDateTime createdDate;

    private PsychologistDetailDTO psychologistDetail;
}

