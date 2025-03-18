package com.example.demo.Repository;

import com.example.demo.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
    Optional<UserDetail> findByUser_UserID(Long userId);
}