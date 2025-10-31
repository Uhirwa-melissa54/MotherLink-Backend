package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.LoginDto;
import com.project.motherlink2.Dtos.LoginResponseDto;
import com.project.motherlink2.model.Admin;
import com.project.motherlink2.repository.AdminRepository;
import com.project.motherlink2.service.AdminService;
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



    @PostMapping("/create")
    public ResponseEntity createAdmin(@RequestBody Admin admin) {
        if (adminService.exists(admin.getFullName(), admin.getEmail(), admin.getOrganization().getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Admin savedAdmin = adminService.save(admin);

            return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully");



    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        if (loginDto == null) {
            return ResponseEntity.status(400)
                    .body(new LoginResponseDto(false, "Login request cannot be null", null));
        }
        String email = loginDto.getEmail() != null ? loginDto.getEmail().trim() : null;
        String password = loginDto.getPassword() != null ? loginDto.getPassword().trim() : null;
        Optional<Admin> admin = adminService.login(email, password);

        if (admin.isPresent()) {
            return ResponseEntity.status(200).body("Logged in successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }
}
