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
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        // Validate and process LoginDto directly
        if (loginDto == null) {
            return ResponseEntity.status(400)
                    .body(new LoginResponseDto(false, "Login request cannot be null", null));
        }
        String email = loginDto.getEmail() != null ? loginDto.getEmail().trim() : null;
        String password = loginDto.getPassword() != null ? loginDto.getPassword().trim() : null;
        String role = loginDto.getPosition() != null ? loginDto.getPosition().trim().toUpperCase() : null;

        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(400)
                    .body(new LoginResponseDto(false, "Email cannot be empty", null));
        }
        if (password == null || password.isEmpty()) {
            return ResponseEntity.status(400)
                    .body(new LoginResponseDto(false, "Password cannot be empty", null));
        }
        if (role == null || role.isEmpty()) {
            return ResponseEntity.status(400)
                    .body(new LoginResponseDto(false, "Role cannot be empty", null));
        }

        // Call the service to authenticate
        return adminService.login(email, role, password)
                .map(admin -> ResponseEntity.ok(new LoginResponseDto(true, "Login successful", admin)))
                .orElseGet(() -> ResponseEntity.status(401)
                        .body(new LoginResponseDto(false, "Invalid email, role, or password", null)));
    }
}
