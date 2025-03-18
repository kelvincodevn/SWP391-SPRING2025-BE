package com.example.demo.service;

import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.PsychologistSlotRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Booking;
import com.example.demo.entity.User;
import com.example.demo.enums.BookingStatus;
import com.example.demo.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PsychologistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PsychologistSlotRepository psychologistSlotRepository;

    @Autowired
    private BookingRepository bookingRepository;

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

    public Map<String, Object> getPsychologistStats(Long psychologistId) {
        User psychologist = userRepository.findById(psychologistId)
                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        long totalSlots = psychologistSlotRepository.countByPsychologistUserID(psychologistId);
        long totalBookings = bookingRepository.countByPsychologistId(psychologistId);
        long completedBookings = bookingRepository.countByPsychologistIdAndStatus(psychologistId, BookingStatus.COMPLETED);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSlots", totalSlots);
        stats.put("totalBookings", totalBookings);
        stats.put("completedBookings", completedBookings);

        return stats;
    }

    public List<Map<String, Object>> getPsychologistClients(Long psychologistId) {
        User psychologist = userRepository.findById(psychologistId)
                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        // Lấy danh sách booking của psychologist
        List<Booking> bookings = bookingRepository.findByPsychologistIdAndStatusNot(psychologistId, BookingStatus.CANCELLED);

        // Chuyển đổi thông tin client từ booking
        List<Map<String, Object>> clients = new ArrayList<>();
        for (Booking booking : bookings) {
            Map<String, Object> client = new HashMap<>();
            client.put("id", booking.getUser().getUserID());
            client.put("fullName", booking.getUser().getFullName());
            client.put("email", booking.getUser().getEmail());
            client.put("bookingId", booking.getId());
            client.put("bookingDate", booking.getSlot().getAvailableDate());
            clients.add(client);
        }

        return clients;
    }

//    public Map<String, Object> getPsychologistProfile(Long psychologistId) {
//        User psychologist = userRepository.findById(psychologistId)
//                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted())
//                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
//
//        Map<String, Object> profile = new HashMap<>();
//        profile.put("id", psychologist.getId());
//        profile.put("fullName", psychologist.getFullName());
//        profile.put("email", psychologist.getEmail());
//        profile.put("major", psychologist.getMajor());
//        profile.put("degree", psychologist.getDegree());
//        profile.put("createdAt", psychologist.getCreatedAt());
//
//        return profile;
//    }

//    public Map<String, Object> updatePsychologistProfile(Long psychologistId, Map<String, Object> profileData) {
//        User psychologist = userRepository.findById(psychologistId)
//                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted())
//                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
//
//        // Cập nhật các trường được gửi từ frontend
//        if (profileData.containsKey("fullName")) {
//            psychologist.setFullName((String) profileData.get("fullName"));
//        }
//        if (profileData.containsKey("email")) {
//            psychologist.setEmail((String) profileData.get("email"));
//        }
//        if (profileData.containsKey("major")) {
//            psychologist.setMajor((String) profileData.get("major"));
//        }
//        if (profileData.containsKey("degree")) {
//            psychologist.setDegree((String) profileData.get("degree"));
//        }
//
//        userRepository.save(psychologist);
//
//        // Trả về profile đã cập nhật
//        Map<String, Object> updatedProfile = new HashMap<>();
//        updatedProfile.put("id", psychologist.getId());
//        updatedProfile.put("fullName", psychologist.getFullName());
//        updatedProfile.put("email", psychologist.getEmail());
//        updatedProfile.put("major", psychologist.getMajor());
//        updatedProfile.put("degree", psychologist.getDegree());
//        updatedProfile.put("createdAt", psychologist.getCreatedAt());
//
//        return updatedProfile;
//    }
}