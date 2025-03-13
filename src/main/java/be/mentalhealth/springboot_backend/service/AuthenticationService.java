package be.mentalhealth.springboot_backend.service;



import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.entity.request.AuthenticationRequest;
import be.mentalhealth.springboot_backend.entity.response.AuthenticationResponse;
import be.mentalhealth.springboot_backend.enums.RoleEnum;
import be.mentalhealth.springboot_backend.repository.AuthenticationRepository;
import jakarta.mail.internet.MimeMessage;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.Random;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    JavaMailSender mailSender;

    public User register(AccountRequest accountRequest){
        //xử lý logic

        //lưu xuống db

        User user = new User();

        user.setUserName(accountRequest.getUsername());
        user.setRoleEnum(RoleEnum.STUDENT);
        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        user.setFullName(accountRequest.getFullName());
        user.setEmail(accountRequest.getEmail());

        // Tạo OTP
        String otp = generateOTP();
        user.setOtpCode(otp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(5));

        User newUser = authenticationRepository.save(user);

        // Gửi email OTP
        sendOtpEmail(newUser.getEmail(), otp);

        return newUser;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername().trim(),
                            authenticationRequest.getPassword().trim()
                    )
            );
        }catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace(); // In ra stack trace để debug

            if (e instanceof BadCredentialsException) {
                throw new BadCredentialsException("Sai thông tin đăng nhập: " + e.getMessage());
            } else if (e instanceof LockedException) {
                throw new LockedException("Tài khoản đã bị khóa: " + e.getMessage());
            } else if (e instanceof DisabledException) {
                throw new DisabledException("Tài khoản đã bị vô hiệu hóa: " + e.getMessage());
            } else if (e instanceof AccountExpiredException) {
                throw new AccountExpiredException("Tài khoản đã hết hạn: " + e.getMessage());
            } else if (e instanceof CredentialsExpiredException) {
                throw new CredentialsExpiredException("Thông tin đăng nhập đã hết hạn: " + e.getMessage());
            } else {
                throw new AuthenticationException("Lỗi xác thực: " + e.getMessage()) {
                };
            }
        }

        User user = authenticationRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        String token = tokenService.generateToken(user);

         AuthenticationResponse authenticationResponse = new AuthenticationResponse();
         authenticationResponse.setEmail(user.getEmail());
         authenticationResponse.setUserID(user.getUserID());
         authenticationResponse.setFullName(user.getFullName());
         authenticationResponse.setUsername(user.getUsername());
         authenticationResponse.setRoleEnum(user.getRoleEnum());
         authenticationResponse.setToken(token);


         return authenticationResponse;

    }
    // Hàm tạo mã OTP
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Mã 6 chữ số
        return String.valueOf(otp);
    }

    // Hàm gửi email OTP
    private void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Xác nhận đăng ký tài khoản");
            helper.setText("Mã OTP của bạn là: " + otp + "\nMã sẽ hết hạn sau 5 phút.", false);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Không thể gửi email OTP: " + e.getMessage());
        }
    }
    public boolean verifyOtp(String username, String otp) {
        User user = authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOtpCode() == null || user.getOtpExpiration() == null) {
            throw new RuntimeException("OTP not found or expired");
        }

        if (LocalDateTime.now().isAfter(user.getOtpExpiration())) {
            throw new RuntimeException("OTP has expired");
        }

        return user.getOtpCode().equals(otp);
    }
}
