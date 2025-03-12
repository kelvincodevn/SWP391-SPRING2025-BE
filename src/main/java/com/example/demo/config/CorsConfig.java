package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Add the Configuration annotation
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply CORS to all endpoints under /api/
                .allowedOrigins("http://localhost:5173") // Allow requests from your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Added OPTIONS method
                .allowedHeaders("*"); // Allowed headers
    }
}
