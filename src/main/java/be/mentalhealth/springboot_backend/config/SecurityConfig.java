package be.mentalhealth.springboot_backend.config;

import be.mentalhealth.springboot_backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

//Class để config spring security
// trước khi chạy chương trình chay giùm file config trc
@Configuration
public class SecurityConfig {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    Filter filter;
    //method để mã hóa password
    //kiểu dữ liệu trả về + tên hàm
    //xài AutoWire để tạo instance của class này
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                         Allow Swagger without authentication
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//                         Allow login & register
//                        .requestMatchers("/api/login", "/api/register").permitAll()
//                         Protect APIs
//                        .requestMatchers("/Manager/**").hasRole("MANAGER")
//                        .requestMatchers("/User/**").hasAnyRole("STUDENT", "PARENT")
//                        .requestMatchers("/Psychologist/**").hasRole("PSYCHOLOGIST")
//                         Profile should be accessible to any logged-in user
//                        .requestMatchers("/api/profile").authenticated()
//                         Any other requests must be authenticated
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                .requestMatchers("/**").permitAll() // /** các đường dẫn sau dấu / cho permit All
                        .anyRequest().authenticated()
                )
                .userDetailsService(authenticationService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }





}