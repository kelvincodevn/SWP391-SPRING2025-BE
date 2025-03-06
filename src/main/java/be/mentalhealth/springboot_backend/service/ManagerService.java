package com.example.demo.service;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.Repository.AuthenticationRepository;
import com.example.demo.Repository.ManagerRepository;
import com.example.demo.entity.User;
import org.modelmapper.ModelMapper;
import com.example.demo.entity.request.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthenticationRepository authenticationRepository;

    public List<User> getAllUser() {
        return managerRepository.findAll();
    }


    public User deleteUser(long UserID) {
        User user = managerRepository.findByUserID(UserID);
        user.isDeleted = true;
        return managerRepository.save(user);
    }

    public User updateUser(Long UserID, AccountRequest user) {
        Optional<User> optionalUser = managerRepository.findById(UserID);
        if (optionalUser.isPresent()) {
            User newUser = optionalUser.get();
            newUser.setUserName(user.getUsername());
            newUser.setFullName(user.getFullName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            return managerRepository.save(newUser);
        } else {
            throw new RuntimeException("User not found with id: " + UserID);
        }
    }
}
