package com.example.demo.service;

import com.example.demo.Repository.UserDetailRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.enums.RoleEnum;
import com.example.demo.entity.request.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
//    @Autowired
//    ManagerRepository managerRepository;

//    @Autowired
//    ModelMapper modelMapper;

//    @Autowired
//    AuthenticationRepository authenticationRepository;

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerService(UserRepository userRepository, UserDetailRepository userDetailRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> getAllUser() {
        return userRepository.findAll().stream()
                .filter(user -> !user.isDeleted)
                .collect(Collectors.toList());
    }

    public User createPsychologist(AccountRequest request) {

        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Invalid request data");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRoleEnum(RoleEnum.PSYCHOLOGIST);

        User savedUser = userRepository.save(user);

        UserDetail detail = UserDetail.builder()
                .user(savedUser)
                .major("")
                .workplace("")
                .degree("")
                .build();
        userDetailRepository.save(detail);

        return savedUser;
    }

    public User updatePsychologist(Long id, AccountRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Invalid request data");
        }

        User user = userRepository.findById(id)
                .filter(u -> !u.isDeleted)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getRoleEnum().equals(RoleEnum.PSYCHOLOGIST)) {
            throw new RuntimeException("User is not a Psychologist");
        }
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    public User deleteUser(Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !u.isDeleted)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.isDeleted = true;
        return userRepository.save(user);
    }
}
