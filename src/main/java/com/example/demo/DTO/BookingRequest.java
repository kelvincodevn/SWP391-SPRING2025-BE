package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private int slotId;
    private String fullName;
    private String gender;

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getGender() {
        return gender;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    private String email;
    private String phoneNumber;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String reason;
    private Double fee;
}

