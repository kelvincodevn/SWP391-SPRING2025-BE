package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    private final UserRepository userRepository;
    private final Random random = new Random();

    public OtpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateOtp() {
        return String.format("%06d", random.nextInt(1000000)); // Tạo OTP 6 chữ số
    }

    public void assignOtpToUser(User user) {
        String otp = generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(5)); // OTP có hạn 5 phút
        userRepository.save(user);
    }

    public boolean validateOtp(String email, String otp) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        if (user.isOtpValid(otp)) {
            user.setStatus(true); // Kích hoạt tài khoản
            user.setOtpCode(null); // Xóa OTP sau khi xác thực
            user.setOtpExpiration(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
