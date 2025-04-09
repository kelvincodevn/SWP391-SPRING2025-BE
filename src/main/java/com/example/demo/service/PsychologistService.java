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

import java.util.List;

@Service
public class PsychologistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    TestResultRepository testResultRepository;

    public User getCurrentPsychologist() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .filter(u -> u.getRoleEnum() == RoleEnum.PSYCHOLOGIST && !u.isDeleted)
                .orElseThrow(() -> new UsernameNotFoundException("Psychologist not found"));
    }

    public List<User> getAllPsychologists() {
        return userRepository.findByRoleEnumAndIsDeletedFalse(RoleEnum.PSYCHOLOGIST);
    }

    public PsychologistDetailsDTO getPsychologistProfile() {
        User psychologist = getCurrentPsychologist();
        UserDetail userDetail = psychologist.getUserDetail();
        if (userDetail == null) {
            throw new RuntimeException("UserDetail not found for this psychologist");
        }

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
        profile.setExperience(userDetail.getExperience()); // Thêm experience

        return profile;
    }

    public PsychologistDetailsDTO updatePsychologistProfile(PsychologistDetailsDTO profileData) {
        User psychologist = getCurrentPsychologist();
        UserDetail userDetail = psychologist.getUserDetail();
        if (userDetail == null) {
            throw new RuntimeException("UserDetail not found for this psychologist");
        }

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
        if (profileData.getExperience() != null) { // Thêm cập nhật experience
            userDetail.setExperience(profileData.getExperience());
        }

        userRepository.save(psychologist);

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
        updatedProfile.setExperience(userDetail.getExperience()); // Thêm experience

        return updatedProfile;
    }

    public List<TestResult> getTestResultsByUserId(Long userId) {
        return testResultRepository.findByUser_UserID(userId);
    }

    public TestResult getTestResultById(Long resultId) {
        return testResultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Test result not found"));
    }
}