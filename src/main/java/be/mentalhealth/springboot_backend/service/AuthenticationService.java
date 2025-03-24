package be.mentalhealth.springboot_backend.service;


import be.mentalhealth.springboot_backend.Repository.AuthenticationRepository;
import be.mentalhealth.springboot_backend.Repository.UserRepository;
import be.mentalhealth.springboot_backend.enums.RoleEnum;

import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.entity.request.AuthenticationRequest;
import be.mentalhealth.springboot_backend.entity.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    public User register(AccountRequest accountRequest){
        //xử lý logic

        //lưu xuống db

        User user = new User();

        user.setUserName(accountRequest.getUsername());
        user.setRoleEnum(RoleEnum.STUDENT);
        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        user.setFullName(accountRequest.getFullName());
        user.setEmail(accountRequest.getEmail());

        User newUser = authenticationRepository.save(user);
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
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
