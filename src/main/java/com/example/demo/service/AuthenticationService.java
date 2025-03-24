package com.example.demo.service;


import com.example.demo.Repository.AuthenticationRepository;

import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.enums.RoleEnum;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        if (userRepository.findByEmail(accountRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = new User();
        user.setUsername(accountRequest.getUsername());
        user.setRoleEnum(accountRequest.getRoleEnum());
        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        user.setFullName(accountRequest.getFullName());
        user.setEmail(accountRequest.getEmail());

        return authenticationRepository.save(user);
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

    public Long getLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUserID();
    }
}
