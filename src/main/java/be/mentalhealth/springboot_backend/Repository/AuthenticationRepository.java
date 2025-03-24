package be.mentalhealth.springboot_backend.Repository;

import be.mentalhealth.springboot_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<User, Long> {

    User findByUserID(long UserID);

   Optional<User> findByUsername(String username);

    List<User> findAll();
}
