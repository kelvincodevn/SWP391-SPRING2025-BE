package com.example.demo.service;

import com.example.demo.Repository.BookingRepository;
import com.example.demo.entity.Booking;
import com.example.demo.enums.BookingStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    private final BookingRepository bookingRepository;

    public PaymentService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
//    @Autowired
//    BookingRepository bookingRepository;

    public String createPaymentUrl(Integer bookingId, HttpServletRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Booking must be CONFIRMED to proceed with payment");
        }

        String orderId = bookingId + "_" + System.currentTimeMillis();
        String amount = String.valueOf((int) (booking.getFee() * 100)); // VNPay dùng đơn vị VND * 100

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", VNPayConfig.VNPAY_TMN_CODE);
        vnpParams.put("vnp_Amount", amount);
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", orderId);
        vnpParams.put("vnp_OrderInfo", "Thanh toan booking #" + bookingId);
        vnpParams.put("vnp_OrderType", "billpayment");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", VNPayConfig.VNPAY_RETURN_URL);
        vnpParams.put("vnp_IpAddr", request.getRemoteAddr());
        vnpParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        String hashData = VNPayConfig.hashAllFields(vnpParams);
        vnpParams.put("vnp_SecureHash", hashData);

        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .append("&");
        }
        query.setLength(query.length() - 1); // Xóa "&" cuối

        return VNPayConfig.VNPAY_URL + "?" + query;
    }

    public void handlePaymentCallback(Map<String, String> params) {
        String vnp_TxnRef = params.get("vnp_TxnRef");
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_SecureHash = params.get("vnp_SecureHash");

        Map<String, String> fields = new HashMap<>(params);
        fields.remove("vnp_SecureHash");
        String calculatedHash = VNPayConfig.hashAllFields(fields);

        if (!calculatedHash.equals(vnp_SecureHash)) {
            throw new RuntimeException("Invalid payment signature");
        }

        Integer bookingId = Integer.parseInt(vnp_TxnRef.split("_")[0]);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("00".equals(vnp_ResponseCode)) {
            booking.setStatus(BookingStatus.PAID);
            bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Payment failed with code: " + vnp_ResponseCode);
        }
    }
}
