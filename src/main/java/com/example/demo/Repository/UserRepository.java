package com.example.demo.Repository;

import com.example.demo.entity.User;
import com.example.demo.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    List<User> findByRoleEnumAndIsDeletedFalse(RoleEnum roleEnum);
//    Optional<User> findByUsernameAndIsDeletedFalse(String username);
    Optional<User> findByUsernameAndIsDeletedFalse(String username);
    Optional<User> findByEmailAndIsDeletedFalse(String email);
    Optional<User> findByEmail(String email);


}
