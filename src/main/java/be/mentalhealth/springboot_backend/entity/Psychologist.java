package be.mentalhealth.springboot_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "psychologist")
@Getter
@Setter

public class Psychologist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer psychoId;

    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phone;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String gender;
    private String avatar;
    private Float serviceFee;
    private Boolean status;


    @CreationTimestamp  // Tự động set giá trị khi tạo mới
    @Column(updatable = false)  // Không cho phép cập nhật sau khi tạo
    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "psychologist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private PsychologistDetail psychologistDetail;

    public Psychologist() {
        // Constructor mặc định bắt buộc cho Hibernate
    }

    public Psychologist(Integer psychoId, String userName, String password, String fullName, String email, String phone, LocalDate dob, String gender, String avatar, Float serviceFee, Boolean status, LocalDateTime createdDate, PsychologistDetail psychologistDetail) {
        this.psychoId = psychoId;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.avatar = avatar;
        this.serviceFee = serviceFee;
        this.status = status;
        this.createdDate = createdDate;
        this.psychologistDetail = psychologistDetail;
    }

    private Psychologist(PsychologistBuilder builder) {
        this.psychoId = builder.psychoId;
        this.userName = builder.userName;
        this.password = builder.password;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.dob = builder.dob;
        this.gender = builder.gender;
        this.avatar = builder.avatar;
        this.serviceFee = builder.serviceFee;
        this.status = builder.status;
        this.createdDate = builder.createdDate;
        this.psychologistDetail = builder.psychologistDetail;
    }


    // ✅ Tạo class Builder thủ công
    public static class PsychologistBuilder {
        private Integer psychoId;
        private String userName;
        private String password;
        private String fullName;
        private String email;
        private String phone;
        private LocalDate dob;
        private String gender;
        private String avatar;
        private Float serviceFee;
        private Boolean status;
        private LocalDateTime createdDate;
        private PsychologistDetail psychologistDetail;

        public PsychologistBuilder() {}

        public PsychologistBuilder psychoId(Integer psychoId) {
            this.psychoId = psychoId;
            return this;
        }

        public PsychologistBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public PsychologistBuilder password(String password) {
            this.password = password;
            return this;
        }

        public PsychologistBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public PsychologistBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PsychologistBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public PsychologistBuilder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public PsychologistBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public PsychologistBuilder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public PsychologistBuilder serviceFee(Float serviceFee) {
            this.serviceFee = serviceFee;
            return this;
        }

        public PsychologistBuilder status(Boolean status) {
            this.status = status;
            return this;
        }

        public PsychologistBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public PsychologistBuilder psychologistDetail(PsychologistDetail psychologistDetail) {
            this.psychologistDetail = psychologistDetail;
            return this;
        }

        // ✅ Quan trọng: Method `build()` để tạo đối tượng Psychologist
        public Psychologist build() {
            return new Psychologist(this);
        }
    }

    // ✅ Hàm khởi tạo Builder
    public static PsychologistBuilder builder() {
        return new PsychologistBuilder();
    }

    public void softDelete() {
        this.status = false;
    }

    public Integer getPsychoId() {
        return psychoId;
    }

    public void setPsychoId(Integer psychoId) {
        this.psychoId = psychoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public PsychologistDetail getPsychologistDetail() {
        return psychologistDetail;
    }

    public void setPsychologistDetail(PsychologistDetail psychologistDetail) {
        this.psychologistDetail = psychologistDetail;
    }

}
