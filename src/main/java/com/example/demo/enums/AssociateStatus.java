package com.example.demo.enums;

public enum AssociateStatus {
    PENDING,    // Yêu cầu đang chờ xác nhận
    CONFIRMED,  // Yêu cầu đã được xác nhận
    DECLINED,   // Yêu cầu bị từ chối
    CANCELLED,  // Yêu cầu đã bị hủy sau khi xác nhận
    EXPIRED     // Yêu cầu đã hết hạn
}