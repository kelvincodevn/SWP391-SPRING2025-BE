package com.example.demo.entity;

import com.example.demo.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    private String fullName;
    private String gender;
    private String email;
    private String phoneNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    private String reason;
    private Double fee;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String medicalReportPath;

    private LocalDateTime confirmationDeadline; // Thời gian hết hạn xác nhận

    // Thêm field createdAt
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Tự động gán giá trị cho createdAt khi tạo mới
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor không tham số
    public Booking() {
    }

    // Constructor có tham số (cập nhật để bao gồm createdAt nếu cần)
    public Booking(User user, Slot slot, String fullName, String gender, String email,
                   String phoneNumber, LocalDate dob, String reason, Double fee, BookingStatus status) {
        this.user = user;
        this.slot = slot;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.reason = reason;
        this.fee = fee;
        this.status = status;
        this.createdAt = LocalDateTime.now(); // Gán giá trị cho createdAt
    }

    // Getter và Setter cho createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Các getter/setter khác giữ nguyên
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getMedicalReportPath() {
        return medicalReportPath;
    }

    public void setMedicalReportPath(String medicalReportPath) {
        this.medicalReportPath = medicalReportPath;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getConfirmationDeadline() {
        return confirmationDeadline;
    }

    public void setConfirmationDeadline(LocalDateTime confirmationDeadline) {
        this.confirmationDeadline = confirmationDeadline;
    }
}