package com.example.demo.enums;

public enum BookingStatus {
    PENDING,     // Chờ psychologist xử lý
    COMPLETED,    // Psychologist đã hoàn thành tư vấn & gửi báo cáo
    CANCELLED,
    AWAITING_CONFIRMATION, // Trạng thái mới,
    PAID
}
