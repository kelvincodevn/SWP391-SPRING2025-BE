package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.entity.Account;
import be.mentalhealth.springboot_backend.entity.request.AccountRequest;
import be.mentalhealth.springboot_backend.enums.RoleEnum;
import be.mentalhealth.springboot_backend.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


//class xử lý API Authentication
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    PasswordEncoder passwordEncoder; //kiểu dữ liệu + instance

    public Account register(AccountRequest accountRequest) {
        // xử lý logic

        // lưu xuống DB
        Account account = new Account(); //tạo account

        account.setRoleEnum(RoleEnum.CUSTOMER); //set role
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword())); // mã hóa password
        account.setFullName(accountRequest.getFullName()); //lấy fullName từ accountRequest set vào
        account.setEmail(accountRequest.getEmail()); //lấy email từ accountRequest set vào

        Account newAccount = authenticationRepository.save(account);
//        Account newAccount = authenticationRepository.save(account);
        return newAccount;
    }

    public Account login(Account account) {
        // xử lý logic
        // lưu xuống DB

        // mã hóa password trc khi lưu xuống DB
//            String currentPassword = account.getPassword();
//            String newPassword = passwordEncoder.encode(currentPassword);
        // account.setPassword(newPassword);

//            account.setRoleEnum(RoleEnum.CUSTOMER);
//            account.setPassword(passwordEncoder.encode(account.getPassword())); // mã hóa password
        Account newAccount = authenticationRepository.save(account);

        return newAccount;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return null;
    }
}
