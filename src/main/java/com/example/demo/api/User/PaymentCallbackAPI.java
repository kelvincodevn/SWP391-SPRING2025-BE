package com.example.demo.api.User;

import com.example.demo.Repository.BookingRepository;
import com.example.demo.service.PaymentService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/vnpay")
public class PaymentCallbackAPI {

    private final PaymentService paymentService;

    public PaymentCallbackAPI(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    BookingRepository bookingRepository;

    @PermitAll
    @PostMapping("/callback")
    public ResponseEntity<String> paymentCallback(@RequestBody Map<String, String> params) {
        paymentService.handlePaymentCallback(params);
        return ResponseEntity.ok("Payment processed successfully");
    }
}
