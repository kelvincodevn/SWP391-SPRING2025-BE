package be.mentalhealth.springboot_backend.Repository;

import be.mentalhealth.springboot_backend.entity.User;
import be.mentalhealth.springboot_backend.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    long countByRoleEnumIn(List<RoleEnum> roles);

}
