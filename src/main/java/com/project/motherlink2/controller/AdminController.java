package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.LoginDto;
import com.project.motherlink2.Dtos.LoginResponseDto;
import com.project.motherlink2.Dtos.RegisterResponseDto;
import com.project.motherlink2.model.Admin;
import com.project.motherlink2.config.JwtUtil;
import com.project.motherlink2.repository.AdminRepository;
import com.project.motherlink2.service.AdminService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;
    private final JwtUtil jwtUtil;


    @PostMapping("/create")
    public ResponseEntity createAdmin(@RequestBody Admin admin, HttpServletResponse response) {
        if (adminService.exists(admin.getFullName(), admin.getEmail(), admin.getOrganization().getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Admin savedAdmin = adminService.save(admin);
        if(savedAdmin!=null) {
            String accessToken = jwtUtil.generateAccessToken(savedAdmin.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(savedAdmin.getEmail());
            addRefreshTokenToCookie(response, refreshToken);
            return ResponseEntity.ok(new RegisterResponseDto(true, "Registered successfull", accessToken));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RegisterResponseDto(false, "Registration failed", null));



    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto,HttpServletResponse response) {
        if (loginDto == null) {
            return ResponseEntity.status(400)
                    .body(new LoginResponseDto(false, "Login request cannot be null", null));
        }
        String email = loginDto.getEmail() != null ? loginDto.getEmail().trim() : null;
        String password = loginDto.getPassword() != null ? loginDto.getPassword().trim() : null;
        Optional<Admin> admin = adminService.login(email, password);

        if (admin.isPresent()) {
            String accessToken = jwtUtil.generateAccessToken(admin.get().getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(admin.get().getEmail());
            addRefreshTokenToCookie(response, refreshToken);
            return ResponseEntity.ok(new LoginResponseDto(true, "Login successful", accessToken));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }
    private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // true if using HTTPS
        cookie.setPath("/api/token"); // endpoint for refresh
        cookie.setMaxAge(24 * 60 * 60); // 1 day
        response.addCookie(cookie);
    }

}
