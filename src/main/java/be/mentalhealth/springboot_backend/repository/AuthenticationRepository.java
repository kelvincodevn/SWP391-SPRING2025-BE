package be.mentalhealth.springboot_backend.repository;


import be.mentalhealth.springboot_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Long> {
    User findByUserID(long UserID);
    Optional<User> findByUsername(String username);
    List<User> findAll();
}
