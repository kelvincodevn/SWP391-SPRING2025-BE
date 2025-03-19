package com.example.demo.enums;

public enum BookingStatus {
    PENDING,     // Chờ psychologist xử lý
    ACCEPTED,    // Được psychologist chấp nhận (chờ user xác nhận)
    CONFIRMED,   // User đã xác nhận booking
    DECLINED,    // Bị psychologist từ chối
    COMPLETED,    // Psychologist đã hoàn thành tư vấn & gửi báo cáo
    CANCELLED,
    PAID
}
