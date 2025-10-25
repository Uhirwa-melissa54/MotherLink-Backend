package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.LoginDto;
import com.project.motherlink2.Dtos.LoginResponseDto;
import com.project.motherlink2.model.Admin;
import com.project.motherlink2.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.saveAdmin(admin);
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
