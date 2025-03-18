package com.example.demo.api.User;

import com.example.demo.Repository.BookingRepository;
import com.example.demo.entity.Booking;
import com.example.demo.enums.BookingStatus;
import com.example.demo.service.PaymentService;
import com.example.demo.service.VNPayConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/vnpay")
@SecurityRequirement(name = "api")
public class PaymentAPI {
    private final PaymentService paymentService;

    public PaymentAPI(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    BookingRepository bookingRepository;


//    @GetMapping("/create")
//    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
//    public ResponseEntity<String> createPayment(@RequestParam Integer bookingId, HttpServletRequest request) {
//        String paymentUrl = paymentService.createPaymentUrl(bookingId, request);
//        return ResponseEntity.ok(paymentUrl);
//    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<String> createPaymentUrl(@RequestParam Integer bookingId, HttpServletRequest request) throws UnsupportedEncodingException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking must be in PENDING status");
        }

        String vnp_TxnRef = bookingId + "_" + System.currentTimeMillis();
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_OrderInfo = "Payment for booking " + bookingId;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.VNPAY_TMN_CODE);
        vnp_Params.put("vnp_Amount", String.valueOf((long) (booking.getFee() * 100)));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", VNPayConfig.orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.VNPAY_RETURN_URL);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", VNPayConfig.getCurrentDateTime());

        String queryString = VNPayConfig.createQueryString(vnp_Params);
        String secureHash = VNPayConfig.hmacSHA512(VNPayConfig.VNPAY_HASH_SECRET, queryString);
        String paymentUrl = VNPayConfig.VNPAY_URL + "?" + queryString + "&vnp_SecureHash=" + secureHash;

        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> paymentCallback(@RequestParam Map<String, String> params) {
        paymentService.handlePaymentCallback(params);
        return ResponseEntity.ok("Payment processed successfully");
    }

    @GetMapping("/success")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'PARENT')")
    public ResponseEntity<Map<String, Object>> paymentSuccess(@RequestParam Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getStatus() != BookingStatus.PAID) {
            throw new RuntimeException("Booking not paid yet");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("bookingId", booking.getId());
        response.put("amount", booking.getFee());
        response.put("date", booking.getSlot().getAvailableDate().toString());
        response.put("time", booking.getSlot().getStartTime() + " - " + booking.getSlot().getEndTime());
        response.put("psychologistName", booking.getSlot().getPsychologistSlot().getPsychologist().getFullName());

        return ResponseEntity.ok(response);
    }


}