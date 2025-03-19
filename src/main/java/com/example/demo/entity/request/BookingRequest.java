package com.example.demo.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private int psychologistSlotId; // Thay slotId bằng psychologistSlotId
    private String fullName;
    private String gender;
    private String email;
    private String phoneNumber;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String reason;
    private Double fee;
}
