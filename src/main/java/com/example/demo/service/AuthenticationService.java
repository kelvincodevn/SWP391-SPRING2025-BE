package com.example.demo.service;


import com.example.demo.Repository.AuthenticationRepository;

import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.enums.RoleEnum;
import com.example.demo.exception.exceptions.InvalidCredentialsException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(User.class);

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

//    public User register(AccountRequest accountRequest){
//        //xử lý logic
//
//        //lưu xuống db
//
//        User user = new User();
//
//        user.setUserName(accountRequest.getUsername());
//        user.setRoleEnum(RoleEnum.STUDENT);
//        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
//        user.setFullName(accountRequest.getFullName());
//        user.setEmail(accountRequest.getEmail());
//
//        User newUser = authenticationRepository.save(user);
//        return newUser;
//    }

    //code cu sẽ xóa sau
//    public User register(AccountRequest accountRequest) {
//        User user = new User();
//        user.setUsername(accountRequest.getUsername());
//        user.setRoleEnum(accountRequest.getRoleEnum() != null ? accountRequest.getRoleEnum() : RoleEnum.STUDENT);
//        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
//        user.setFullName(accountRequest.getFullName());
//        user.setEmail(accountRequest.getEmail());
//        return authenticationRepository.save(user);
//    }

//    public User register(AccountRequest accountRequest) {
//        // Kiểm tra vai trò hợp lệ
//        if (accountRequest.getRoleEnum() == null ||
//                !(accountRequest.getRoleEnum() == RoleEnum.STUDENT || accountRequest.getRoleEnum() == RoleEnum.PARENT)) {
//            throw new IllegalArgumentException("Invalid role. Only STUDENT or PARENT roles are allowed for registration.");
//        }
//
//        // Kiểm tra xem username đã tồn tại chưa
//        if (userRepository.findByUsername(accountRequest.getUsername()).isPresent()) {
//            throw new IllegalArgumentException("Username already exists.");
//        }
//
//        // Kiểm tra xem email đã tồn tại chưa
//        if (userRepository.findByEmail(accountRequest.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email already exists.");
//        }
//
//        User user = new User();
//        user.setUsername(accountRequest.getUsername());
//        user.setRoleEnum(accountRequest.getRoleEnum());
//        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
//        user.setFullName(accountRequest.getFullName());
//        user.setEmail(accountRequest.getEmail());
//
//        return authenticationRepository.save(user);
//    }

    public User register(AccountRequest accountRequest) {
        // Kiểm tra vai trò hợp lệ
        if (accountRequest.getRoleEnum() == null ||
                !(accountRequest.getRoleEnum() == RoleEnum.STUDENT || accountRequest.getRoleEnum() == RoleEnum.PARENT)) {
            throw new IllegalArgumentException("Invalid role. Only STUDENT or PARENT roles are allowed for registration.");
        }

        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(accountRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.findByEmailAndIsDeletedFalse(accountRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        try {
            User user = new User();
            user.setUsername(accountRequest.getUsername());
            user.setRoleEnum(accountRequest.getRoleEnum());
            user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
            user.setFullName(accountRequest.getFullName());
            user.setEmail(accountRequest.getEmail());

            return authenticationRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already exists in the database.", e);
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return authenticationRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleEnum().name())); // Thêm vai trò

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername().trim(),
                            authenticationRequest.getPassword().trim()
                    )
            );
        } catch (BadCredentialsException e) {
            log.error("Authentication failed: Incorrect credentials", e);
            throw new InvalidCredentialsException("Sai thông tin đăng nhập.");
        } catch (LockedException e) {
            log.error("Authentication failed: Account is locked", e);
            throw new LockedException("Tài khoản đã bị khóa.");
        } catch (DisabledException e) {
            log.error("Authentication failed: Account is disabled", e);
            throw new DisabledException("Tài khoản đã bị vô hiệu hóa.");
        } catch (AccountExpiredException e) {
            log.error("Authentication failed: Account has expired", e);
            throw new AccountExpiredException("Tài khoản đã hết hạn.");
        } catch (CredentialsExpiredException e) {
            log.error("Authentication failed: Credentials have expired", e);
            throw new CredentialsExpiredException("Thông tin đăng nhập đã hết hạn.");
        } catch (AuthenticationException e) {
            log.error("Authentication failed: Unexpected authentication error", e);
            throw new AuthenticationException("Lỗi xác thực: " + e.getMessage()) {};
        }

        // Lấy user từ DB, nếu không tìm thấy thì báo lỗi
        User user = authenticationRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> {
                    log.error("Authentication failed: User not found");
                    return new InvalidCredentialsException("Sai thông tin đăng nhập.");
                });

        // Tạo JWT token
        String token = tokenService.generateToken(user);

        // Khởi tạo AuthenticationResponse
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail(user.getEmail());
        authenticationResponse.setUserID(user.getUserID());
        authenticationResponse.setFullName(user.getFullName());
        authenticationResponse.setUsername(user.getUsername());
        authenticationResponse.setRoleEnum(user.getRoleEnum());
        authenticationResponse.setToken(token);

        return authenticationResponse;
    }

    public Long getLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUserID();
    }
}
