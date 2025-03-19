package com.example.demo.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class VNPayConfig {
    public static final String VNPAY_TMN_CODE = "83U5OKQ7"; // Thay bằng mã TMN từ VNPay Sandbox
    public static final String VNPAY_HASH_SECRET = "TSAYTO26IOINY16YDY8V5CQPS08LXCZU"; // Thay bằng secret từ VNPay Sandbox
    public static final String VNPAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String VNPAY_RETURN_URL = "http://localhost:5173/payment-callback";
    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String orderType = "250000";

    // Phương thức để lấy IP address từ request
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

//    // Phương thức để lấy thời gian hiện tại dưới dạng chuỗi
//    public static String getCurrentDateTime() {
//        return String.valueOf(System.currentTimeMillis());
//    }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    // Phương thức để tạo query string từ Map
    public static String createQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        TreeMap<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                query.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                query.append("=");
                query.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                query.append("&");
            }
        }
        return query.length() > 0 ? query.substring(0, query.length() - 1) : ""; // Xóa "&" cuối cùng
    }

//    public static String hashAllFields(Map<String, String> fields) {
//        List<String> fieldNames = new ArrayList<>(fields.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder sb = new StringBuilder();
//        for (String fieldName : fieldNames) {
//            String fieldValue = fields.get(fieldName);
//            if (fieldValue != null && !fieldValue.isEmpty()) {
//                sb.append(fieldName).append("=").append(fieldValue).append("&");
//            }
//        }
//        sb.setLength(sb.length() - 1); // Xóa "&" cuối
//        return HmacSHA512(sb.toString(), VNPayConfig.VNPAY_HASH_SECRET);
//    }

    public static String hashAllFields(Map<String, String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty() && !fieldName.equals("vnp_SecureHash")) {
                sb.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append("&");
            }
        }
        sb.setLength(sb.length() - 1); // Xóa "&" cuối
        return HmacSHA512(sb.toString(), VNPayConfig.VNPAY_HASH_SECRET);
    }

//    public static String hashAllFields(Map<String, String> fields) {
//        List<String> fieldNames = new ArrayList<>(fields.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder sb = new StringBuilder();
//        for (String fieldName : fieldNames) {
//            String fieldValue = fields.get(fieldName);
//            if (fieldValue != null && !fieldValue.isEmpty() && !fieldName.equals("vnp_SecureHash")) {
//                sb.append(fieldName).append("=").append(fieldValue).append("&"); // Không encode ở đây
//            }
//        }
//        sb.setLength(sb.length() - 1); // Xóa "&" cuối
//        return HmacSHA512(sb.toString(), VNPayConfig.VNPAY_HASH_SECRET);
//    }

    private static String HmacSHA512(String data, String key) {
        try {
            Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            hmacSHA512.init(secretKey);
            byte[] bytes = hmacSHA512.doFinal(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC SHA512", e);
        }
    }

    public static String hmacSHA512(String data, String key) {
        try {
            Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmacSHA512.init(secretKey);
            byte[] bytes = hmacSHA512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC SHA512", e);
        }
    }
}