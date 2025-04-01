package be.mentalhealth.springboot_backend.service;


import be.mentalhealth.springboot_backend.Repository.AuthenticationRepository;
import be.mentalhealth.springboot_backend.Repository.UserRepository;
import be.mentalhealth.springboot_backend.enums.RoleEnum;

import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.entity.request.AuthenticationRequest;
import be.mentalhealth.springboot_backend.entity.response.AuthenticationResponse;
import be.mentalhealth.springboot_backend.exception.exceptions.InvalidCredentialsException;
import be.mentalhealth.springboot_backend.exception.exceptions.UserAlreadyExistsException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(User.class);

    public User register(AccountRequest accountRequest){
        //xử lý logic
        if (userRepository.findByUsername(accountRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists!");
        }
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
