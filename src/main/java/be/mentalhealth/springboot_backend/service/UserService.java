package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.Repository.UserRepository;
import be.mentalhealth.springboot_backend.DTO.UserProfileDTO;
import be.mentalhealth.springboot_backend.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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
}
