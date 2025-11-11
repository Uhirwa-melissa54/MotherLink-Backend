package com.project.motherlink2.config;

import com.project.motherlink2.Middleware.Auth;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final Auth authMiddleware; // inject your JWT middleware

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admins/create", "/api/admins/login").permitAll() // public endpoints
                        .anyRequest().authenticated() // all other endpoints require authentication
                )
                .addFilterBefore(authMiddleware, UsernamePasswordAuthenticationFilter.class); // run middleware before default filter

        return http.build();
    }
}
