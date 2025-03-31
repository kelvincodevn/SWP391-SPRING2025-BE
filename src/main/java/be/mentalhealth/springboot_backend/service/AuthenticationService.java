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

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return user;
    }

    public Long getLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUserID();
    }
}
