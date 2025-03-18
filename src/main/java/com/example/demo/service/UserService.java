package com.example.demo.service;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.Repository.AuthenticationRepository;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.ManagerRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.enums.BookingStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookingRepository bookingRepository;

    // Lấy thông tin user hiện tại từ Security Context
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Lấy profile với ModelMapper
    public UserProfileDTO getUserProfile() {
        User user = getCurrentUser();
        return modelMapper.map(user, UserProfileDTO.class); // Chuyển đổi User -> UserProfileDTO
    }

    // Cập nhật profile
    public UserProfileDTO updateUserProfile(UserProfileDTO updatedProfile) {
        User user = getCurrentUser();
        user.setFullName(updatedProfile.getFullName());
        user.setEmail(updatedProfile.getEmail());
        user.setDob(updatedProfile.getDob());
        user.setPhone(updatedProfile.getPhone());
        user.setGender(updatedProfile.getGender());
        user.setAvatar(updatedProfile.getAvatar());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserProfileDTO.class);
    }

//    public Map<String, Object> getUserStats(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        long totalBookings = bookingRepository.countByUserUserID(userId);
//        long completedBookings = bookingRepository.countByUserIdAndStatus(userId, BookingStatus.COMPLETED);
//        long cancelledBookings = bookingRepository.countByUserIdAndStatus(userId, BookingStatus.CANCELLED);
//
//        Map<String, Object> stats = new HashMap<>();
//        stats.put("totalBookings", totalBookings);
//        stats.put("completedBookings", completedBookings);
//        stats.put("cancelledBookings", cancelledBookings);
//
//        return stats;
//    }
}
