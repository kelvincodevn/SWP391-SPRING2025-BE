package com.example.demo.service;

import com.example.demo.DTO.PsychologistDetailsDTO;
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

//    public User createPsychologist(AccountRequest request) {
//
//        if (request == null || request.getUsername() == null || request.getPassword() == null) {
//            throw new IllegalArgumentException("Invalid request data");
//        }
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setFullName(request.getFullName());
//        user.setEmail(request.getEmail());
//        user.setRoleEnum(RoleEnum.PSYCHOLOGIST);
//
//        User savedUser = userRepository.save(user);
//
//        UserDetail detail = UserDetail.builder()
//                .user(savedUser)
//                .major("")
//                .workplace("")
//                .degree("")
//                .fee(Double.parseDouble("150000"))
//                .build();
//        userDetailRepository.save(detail);
//
//        return savedUser;
//    }

    public User createPsychologist(AccountRequest request) {
        // Kiểm tra dữ liệu đầu vào
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Dữ liệu yêu cầu không hợp lệ");
        }

        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Tên người dùng đã tồn tại");
        }

        // Kiểm tra xem email đã tồn tại chưa (chỉ kiểm tra các bản ghi chưa bị xóa mềm)
        if (userRepository.findByEmailAndIsDeletedFalse(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        // Tạo người dùng mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRoleEnum(RoleEnum.PSYCHOLOGIST);

        // Lưu người dùng vào cơ sở dữ liệu
        User savedUser = userRepository.save(user);

        // Tạo và lưu thông tin chi tiết của nhà tâm lý học
        UserDetail detail = UserDetail.builder()
                .user(savedUser)
                .major("")
                .workplace("")
                .degree("")
                .fee(Double.parseDouble("150000"))
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

    // Thêm phương thức mới để xóa psychologist
    public User deletePsychologist(Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !u.isDeleted)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        if (!user.getRoleEnum().equals(RoleEnum.PSYCHOLOGIST)) {
            throw new RuntimeException("User is not a Psychologist");
        }

        user.isDeleted = true;
        return userRepository.save(user);
    }

    // Thêm phương thức mới để lấy chi tiết psychologist
    public PsychologistDetailsDTO getPsychologistDetails(Long userId) {
        User user = userRepository.findById(userId)
                .filter(u -> !u.isDeleted)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRoleEnum().equals(RoleEnum.PSYCHOLOGIST)) {
            throw new RuntimeException("User is not a Psychologist");
        }

        UserDetail userDetail = userDetailRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("UserDetail not found for this psychologist"));

        return new PsychologistDetailsDTO(
                user.getUserID(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getDob(),
                user.getPhone(),
                user.getCreatedDate(),
                user.getStatus(),
                user.getGender(),
                user.getAvatar(),
                userDetail.getMajor(),
                userDetail.getDegree(),
                userDetail.getWorkplace(),
                userDetail.getFee(), // Thêm fee vào DTO
                userDetail.getExperience()
        );
    }
}
