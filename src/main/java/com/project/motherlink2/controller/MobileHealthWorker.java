package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.LoginDto;
import com.project.motherlink2.Dtos.LoginResponseDto;
import com.project.motherlink2.Dtos.RegisterResponseDto;
import com.project.motherlink2.config.JwtUtil;
import com.project.motherlink2.model.Admin;
import com.project.motherlink2.model.CHW;
import com.project.motherlink2.service.CHWService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/mobile/healthworkers")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class MobileHealthWorker {
    private final CHWService chwService;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> createCHW(@RequestBody CHW chw, HttpServletResponse response) {
        CHW savedCHW = chwService.saveCHW(chw);
        if(savedCHW!=null){
            String accessToken = jwtUtil.generateAccessToken(savedCHW.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(savedCHW.getEmail());
            addRefreshTokenToCookie(response, refreshToken);
            return ResponseEntity.ok(new RegisterResponseDto(true, "Registered successfull", accessToken));

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RegisterResponseDto(false, "Registration failed", null));

    }
    private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // true if using HTTPS
        cookie.setPath("/api/token"); // endpoint for refresh
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
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


}
