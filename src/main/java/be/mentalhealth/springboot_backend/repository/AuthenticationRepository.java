package be.mentalhealth.springboot_backend.repository;

import be.mentalhealth.springboot_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//extend để kế thừa và sử dụng những bộ công uuj mà dataJPA đã có sẵn
public interface AuthenticationRepository extends JpaRepository<Account, Long> {



}
