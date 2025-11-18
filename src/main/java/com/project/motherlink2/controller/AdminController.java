package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.*;
import com.project.motherlink2.model.Admin;
import com.project.motherlink2.config.JwtUtil;
import com.project.motherlink2.model.Appointments;
import com.project.motherlink2.model.CHW;
import com.project.motherlink2.service.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admins")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    private final NotificationService notificationService;
    private final AdminService adminService;
    private final JwtUtil jwtUtil;
    private final MotherService motherService;
    private final AuthService authService;
    private final AppointmentService  appointmentService;


    private final CHWService chwService;
    @PostMapping("/notifications/send")
    public ResponseEntity<String> sendNotification(String message){
        try{

            notificationService.sendNotification(message);
            return ResponseEntity.status(HttpStatus.CREATED).body("Notification Sent");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }



    @PostMapping("/create")
    public ResponseEntity<RegisterResponseDto> createAdmin(@RequestBody Admin admin, HttpServletResponse response) {

        if (adminService.exists(admin.getFullName(), admin.getEmail(), admin.getOrganization().getLicenseNumber()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Admin savedAdmin = adminService.save(admin);
        if(savedAdmin!=null) {
            String accessToken = jwtUtil.generateAccessToken(savedAdmin.getEmail(),savedAdmin.getOrganization().getDistrict(),savedAdmin.getOrganization().getSector());
            String refreshToken = jwtUtil.generateRefreshToken(savedAdmin.getEmail(), savedAdmin.getFullName(),savedAdmin.getOrganization().getDistrict(),savedAdmin.getOrganization().getSector());
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
                    .body(new LoginResponseDto(false, "Login request cannot be null", null,"No name"));
        }
        String email = loginDto.getEmail() != null ? loginDto.getEmail().trim() : null;
        String password = loginDto.getPassword() != null ? loginDto.getPassword().trim() : null;
        Optional<Admin> admin = adminService.login(email, password);

        if (admin.isPresent()) {


            String accessToken = jwtUtil.generateAccessToken(admin.get().getEmail(),admin.get().getOrganization().getDistrict(),admin.get().getOrganization().getSector());
            String refreshToken = jwtUtil.generateRefreshToken(admin.get().getEmail(),admin.get().getFullName(),admin.get().getOrganization().getDistrict(),admin.get().getOrganization().getSector());
            addRefreshTokenToCookie(response, refreshToken);
            return ResponseEntity.ok(new LoginResponseDto(true, "Login successful", accessToken,admin.get().getFullName()));
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
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateCHW(@PathVariable Long id) {
        boolean success = chwService.deactivateCHW(id);
        if (success) {
            return ResponseEntity.ok("CHW deactivated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CHW not found");
    }
    @PutMapping("/update/chw")
    public ResponseEntity<?> updateCHW(@PathVariable Long id, CHW updatedCHW) {
        boolean success = chwService.updateCHW(id,updatedCHW);
        if (success) {
            return ResponseEntity.ok("CHW updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CHW not found");
    }


    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token is missing");
        }

        // Validate refresh token
        if (!jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired refresh token");
        }


        String email = jwtUtil.getUsername(refreshToken);
        String name = jwtUtil.getName(refreshToken);
        String sector=jwtUtil.extractSector(refreshToken);
        String district=jwtUtil.extractDistrict(refreshToken);


        String newAccessToken = jwtUtil.generateAccessToken(email,district,sector);

        return ResponseEntity.ok(new LoginResponseDto(true, "Access token refreshed successfully", newAccessToken,name));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateCHW(@PathVariable Long id) {
        boolean success = chwService.activateCHW(id);
        if (success) {
            return ResponseEntity.ok("CHW activated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CHW not found");
    }
@GetMapping("/mothers/children")
    public ResponseEntity<AmbulanceDto> getCHWChildren(HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        long children= motherService.getTotalChildren(district,sector);
        return ResponseEntity.ok(new AmbulanceDto(children));

}

@GetMapping("mothers/pregnantMothers")
    public ResponseEntity<AmbulanceDto> getPregnantMothers(HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        long mothers = motherService.getPregnantMothers(district,sector);
        return ResponseEntity.ok(new AmbulanceDto(mothers));
}

    @GetMapping("mothers/appointments/anc")
    public ResponseEntity<List<AppointementDto>> getAncMothers(HttpServletRequest request) {
        String district = authService.getUserDistrict(request);
        String sector = authService.getUserSector(request);

        List<Appointments> appointments = appointmentService.getAncAppointments(district, sector);

        List<AppointementDto> appointementDtos = appointments.stream()
                .map(n -> new AppointementDto(n.getStatus(), n.getMother().getNames()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(appointementDtos);
    }


 




}
