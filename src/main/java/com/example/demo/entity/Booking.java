package com.example.demo.entity;


import com.example.demo.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
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
    private BookingStatus status; // "Pending", "Accepted", "Declined", "Confirmed", "Completed"

    private String medicalReportPath; // Đường dẫn file kết quả khám bệnh

    // Constructor không tham số (bắt buộc cho JPA)
    public Booking() {}

    // Constructor có tham số
    public Booking(Slot slot, String fullName, String gender, String email,
                   String phoneNumber, LocalDate dob, String reason, Double fee, BookingStatus status) {
        this.slot = slot;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.reason = reason;
        this.fee = fee;
        this.status = status;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
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
}

