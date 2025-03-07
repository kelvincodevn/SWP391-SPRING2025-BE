package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.Repository.AuthenticationRepository;
import be.mentalhealth.springboot_backend.Repository.ManagerRepository;
import be.mentalhealth.springboot_backend.entity.User;
import org.modelmapper.ModelMapper;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
