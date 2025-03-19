package com.example.demo.DTO;

import lombok.Data;

@Data
public class BookingResponse {
    private int bookingId;
    private String status;
    private String message;
    private int slotId;
    private String date;
    private String startTime;
    private String endTime;
    private String psychologistName;
    private Double fee;
    private PsychologistDetails psychologistDetails;
    private ClientDetails clientDetails; // Đảm bảo trường này được khai báo đúng

    public BookingResponse() {
    }

    public BookingResponse(int bookingId, String status, String message) {
        this.bookingId = bookingId;
        this.status = status;
        this.message = message;
    }

    public BookingResponse(
            int bookingId,
            String status,
            String message,
            int slotId,
            String date,
            String startTime,
            String endTime,
            String psychologistName,
            Double fee
    ) {
        this.bookingId = bookingId;
        this.status = status;
        this.message = message;
        this.slotId = slotId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.psychologistName = psychologistName;
        this.fee = fee;
    }

    @Data
    public static class PsychologistDetails {
        private Long userId;
        private String fullName;
        private String major;
        private String degree;
        private String workplace;
        private Double fee;

        public PsychologistDetails() {
        }

        public PsychologistDetails(Long userId, String fullName, String major, String degree, String workplace, Double fee) {
            this.userId = userId;
            this.fullName = fullName;
            this.major = major;
            this.degree = degree;
            this.workplace = workplace;
            this.fee = fee;
        }
    }

    @Data
    public static class ClientDetails {
        private Long userId;
        private String fullName;
        private String email;

        public ClientDetails() {
        }

        public ClientDetails(Long userId, String fullName, String email) {
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
        }
    }

    // Đảm bảo getter và setter cho clientDetails được sinh ra bởi Lombok
    // Nếu Lombok không hoạt động, bạn có thể thêm thủ công:
    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }
}