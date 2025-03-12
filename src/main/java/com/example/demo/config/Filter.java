package com.example.demo.config;


import com.example.demo.entity.User;
import com.example.demo.exception.exceptions.AuthorizeException;
import com.example.demo.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;


@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    @Autowired
    TokenService tokenService;

    List<String> PUBLIC_API = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/auth/login", // Update login path
            "/api/auth/register" // Update register path
    );

    boolean isPermitted(String uri) {
        AntPathMatcher patchMatch = new AntPathMatcher();
        return PUBLIC_API.stream().anyMatch(item -> patchMatch.match(item, uri));
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // filterChain.doFilter(request,response); // cho phép truy cập vào controller
//
//        // check trước khi cho truy cập
//
//        String uri = request.getRequestURI();
//        if (isPermitted(uri)) {
//            // public API
//            filterChain.doFilter(request, response);
//        } else {
//            // không phải là public API => check role
//            String token = getToken(request);
//
//            if (token == null) {
//                // chưa đăng nhập => quăng lỗi
//                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is missing!"));
//            }
//
//            User user = null;
//            try {
//                user = tokenService.getAccountByToken(token);
//            } catch (MalformedJwtException malformedJwtException) {
//                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is invalid!"));
//            } catch (ExpiredJwtException expiredJwtException) {
//                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is expired!"));
//            } catch (Exception exception) {
//                resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is invalid!"));
//            }
//
//            // => token chuẩn
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
//            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//            filterChain.doFilter(request, response);
//        }
//    }
//
//    String getToken(HttpServletRequest request) {
//        return request.getHeader("Authorization").substring(7);
//    }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (isPermitted(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request, response);
        if (token == null) return; // Nếu token không hợp lệ, đã xử lý trong extractToken

        authenticateUser(token, request, response, filterChain);
    }

    private String extractToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            resolver.resolveException(request, response, null, new AuthorizeException("Authorization header is missing or invalid!"));
            return null;
        }
    }

    private void authenticateUser(String token, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        User user = null;
        try {
            user = tokenService.getAccountByToken(token);
        } catch (MalformedJwtException e) {
            resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is invalid!"));
            return;
        } catch (ExpiredJwtException e) {
            resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is expired!"));
            return;
        } catch (Exception e) {
            resolver.resolveException(request, response, null, new AuthorizeException("Authentication token is invalid!"));
            return;
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}


// Bearer ajsdalksjdk;asjdl;adsasjldak;



