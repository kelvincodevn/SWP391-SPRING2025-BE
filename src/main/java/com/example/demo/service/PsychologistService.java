package com.example.demo.service;

import com.example.demo.DTO.PsychologistDetailsDTO;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.TestResultRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.TestResult;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PsychologistService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PsychologistSlotRepository psychologistSlotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    TestResultRepository testResultRepository;

    // Lấy thông tin psychologist hiện tại từ Security Context
    public User getCurrentPsychologist() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted)
                .orElseThrow(() -> new UsernameNotFoundException("Psychologist not found"));
    }

    public List<User> getAllPsychologists() {
        return userRepository.findByRoleEnumAndIsDeletedFalse(RoleEnum.PSYCHOLOGIST);
    }

//    public Map<String, Object> getPsychologistStats(Long psychologistId) {
//        User psychologist = userRepository.findById(psychologistId)
//                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted())
//                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
//
//        long totalSlots = psychologistSlotRepository.countByPsychologistId(psychologistId);
//        long totalBookings = bookingRepository.countByPsychologistId(psychologistId);
//        long completedBookings = bookingRepository.countByPsychologistIdAndStatus(psychologistId, BookingStatus.COMPLETED);
//
//        Map<String, Object> stats = new HashMap<>();
//        stats.put("totalSlots", totalSlots);
//        stats.put("totalBookings", totalBookings);
//        stats.put("completedBookings", completedBookings);
//
//        return stats;
//    }
//
//    public List<Map<String, Object>> getPsychologistClients(Long psychologistId) {
//        User psychologist = userRepository.findById(psychologistId)
//                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted())
//                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
//
//        // Lấy danh sách booking của psychologist
//        List<Booking> bookings = bookingRepository.findByPsychologistIdAndStatusNot(psychologistId, BookingStatus.CANCELLED);
//
//        // Chuyển đổi thông tin client từ booking
//        List<Map<String, Object>> clients = new ArrayList<>();
//        for (Booking booking : bookings) {
//            Map<String, Object> client = new HashMap<>();
//            client.put("id", booking.getUser().getUserID());
//            client.put("fullName", booking.getUser().getFullName());
//            client.put("email", booking.getUser().getEmail());
//            client.put("bookingId", booking.getId());
//            client.put("bookingDate", booking.getSlot().getAvailableDate());
//            clients.add(client);
//        }
//
//        return clients;
//    }

//    public Map<String, Object> getPsychologistStats(Long psychologistId) {
//        User psychologist = userRepository.findById(psychologistId)
//                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST)
//                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
//
//        long totalSlots = psychologistSlotRepository.countByPsychologistUserID(psychologistId);
//        long totalBookings = bookingRepository.countByPsychologistId(psychologistId);
//        long completedBookings = bookingRepository.countByPsychologistIdAndStatus(psychologistId, BookingStatus.COMPLETED);
//
//        Map<String, Object> stats = new HashMap<>();
//        stats.put("totalSlots", totalSlots);
//        stats.put("totalBookings", totalBookings);
//        stats.put("completedBookings", completedBookings);
//
//        return stats;
//    }

//    public List<Map<String, Object>> getPsychologistClients(Long psychologistId) {
//        User psychologist = userRepository.findById(psychologistId)
//                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST)
//                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
//
//        // Lấy danh sách booking của psychologist
//        List<Booking> bookings = bookingRepository.findByPsychologistIdAndStatusNot(psychologistId, BookingStatus.CANCELLED);
//
//        // Chuyển đổi thông tin client từ booking
//        List<Map<String, Object>> clients = new ArrayList<>();
//        for (Booking booking : bookings) {
//            Map<String, Object> client = new HashMap<>();
//            client.put("id", booking.getUser().getUserID());
//            client.put("fullName", booking.getUser().getFullName());
//            client.put("email", booking.getUser().getEmail());
//            client.put("bookingId", booking.getId());
//            client.put("bookingDate", booking.getSlot().getAvailableDate());
//            clients.add(client);
//        }
//
//        return clients;
//    }

    public PsychologistDetailsDTO getPsychologistProfile() {
        User psychologist = getCurrentPsychologist();
        UserDetail userDetail = psychologist.getUserDetail();
        if (userDetail == null) {
            throw new RuntimeException("UserDetail not found for this psychologist");
        }

        // Map User and UserDetail to PsychologistDetailsDTO
        PsychologistDetailsDTO profile = new PsychologistDetailsDTO();
        profile.setUsername(psychologist.getUsername());
        profile.setFullName(psychologist.getFullName());
        profile.setEmail(psychologist.getEmail());
        profile.setDob(psychologist.getDob());
        profile.setPhone(psychologist.getPhone());
        profile.setCreatedDate(psychologist.getCreatedDate());
        profile.setStatus(psychologist.getStatus());
        profile.setGender(psychologist.getGender());
        profile.setAvatar(psychologist.getAvatar());
        profile.setMajor(userDetail.getMajor());
        profile.setDegree(userDetail.getDegree());
        profile.setWorkplace(userDetail.getWorkplace());
        profile.setFee(userDetail.getFee());

        return profile;
    }

    public PsychologistDetailsDTO updatePsychologistProfile(PsychologistDetailsDTO profileData) {
        User psychologist = getCurrentPsychologist();
        UserDetail userDetail = psychologist.getUserDetail();
        if (userDetail == null) {
            throw new RuntimeException("UserDetail not found for this psychologist");
        }

        // Update fields in User entity
        if (profileData.getUsername() != null) {
            psychologist.setUsername(profileData.getUsername());
        }
        if (profileData.getFullName() != null) {
            psychologist.setFullName(profileData.getFullName());
        }
        if (profileData.getEmail() != null) {
            psychologist.setEmail(profileData.getEmail());
        }
        if (profileData.getDob() != null) {
            psychologist.setDob(profileData.getDob());
        }
        if (profileData.getPhone() != null) {
            psychologist.setPhone(profileData.getPhone());
        }
        if (profileData.getGender() != null) {
            psychologist.setGender(profileData.getGender());
        }
        if (profileData.getAvatar() != null) {
            psychologist.setAvatar(profileData.getAvatar());
        }

        // Update fields in UserDetail entity
        if (profileData.getMajor() != null) {
            userDetail.setMajor(profileData.getMajor());
        }
        if (profileData.getDegree() != null) {
            userDetail.setDegree(profileData.getDegree());
        }
        if (profileData.getWorkplace() != null) {
            userDetail.setWorkplace(profileData.getWorkplace());
        }
        if (profileData.getFee() != null) {
            userDetail.setFee(profileData.getFee());
        }

        // Save the updated User (which will cascade to UserDetail due to CascadeType.ALL)
        userRepository.save(psychologist);

        // Map the updated User and UserDetail to PsychologistDetailsDTO
        PsychologistDetailsDTO updatedProfile = new PsychologistDetailsDTO();
        updatedProfile.setUsername(psychologist.getUsername());
        updatedProfile.setFullName(psychologist.getFullName());
        updatedProfile.setEmail(psychologist.getEmail());
        updatedProfile.setDob(psychologist.getDob());
        updatedProfile.setPhone(psychologist.getPhone());
        updatedProfile.setCreatedDate(psychologist.getCreatedDate());
        updatedProfile.setStatus(psychologist.getStatus());
        updatedProfile.setGender(psychologist.getGender());
        updatedProfile.setAvatar(psychologist.getAvatar());
        updatedProfile.setMajor(userDetail.getMajor());
        updatedProfile.setDegree(userDetail.getDegree());
        updatedProfile.setWorkplace(userDetail.getWorkplace());
        updatedProfile.setFee(userDetail.getFee());

        return updatedProfile;
    }

//    public List<Booking> findBookingsByPsychologistUserId(Long userId) {
//        return bookingRepository.findByPsychologistSlot_Psychologist_UserID(userId);
//    }

    // Lấy danh sách bài test mà khách hàng đã làm
    public List<TestResult> getTestResultsByUserId(Long userId) {
        return testResultRepository.findByUser_UserID(userId);
    }

    // Lấy chi tiết kết quả bài test
    public TestResult getTestResultById(Long resultId) {
        return testResultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Test result not found"));
    }
}