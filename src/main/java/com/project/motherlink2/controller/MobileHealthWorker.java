package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.*;
import com.project.motherlink2.config.JwtUtil;
import com.project.motherlink2.service.*;
import com.project.motherlink2.model.CHW;
import com.project.motherlink2.model.Mother;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.project.motherlink2.model.Notification;
import com.project.motherlink2.repository.NotificationRepository;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mobile/healthworkers")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:8081",
    "exp://10.12.72.167:8081"
})
@AllArgsConstructor
public class MobileHealthWorker {
    private final CHWService chwService;
    private final JwtUtil jwtUtil;
    private final MotherService motherService;
    private final NotificationService notificationService;
    private final UpdaterService updaterService;
    private final AppointmentService appointmentService;
    private final NotificationRepository notificationRepository;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> createCHW(@RequestBody CHW chw, HttpServletResponse response) {
        CHW savedCHW = chwService.saveCHW(chw);
        if(savedCHW!=null){
            String accessToken = jwtUtil.generateAccessToken(savedCHW.getEmail(), savedCHW.getCell(), savedCHW.getVillage());
            String refreshToken = jwtUtil.generateRefreshToken(savedCHW.getEmail(), savedCHW.getFullName(), savedCHW.getCell(),  savedCHW.getVillage());
            addRefreshTokenToCookie(response, refreshToken);
            return ResponseEntity.ok(new RegisterResponseDto(true, "Registered successfull", accessToken,savedCHW.getFullName()));

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RegisterResponseDto(false, "Registration failed", null,"No name"));

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
                    .body(new LoginResponseDto(false, "Login request cannot be null", null,"No name"));
        }
        String email = loginDto.getEmail() != null ? loginDto.getEmail().trim() : null;
        String password = loginDto.getPassword() != null ? loginDto.getPassword().trim() : null;
        Optional<CHW> chwOptional = chwService.login(email, password);

        if (chwOptional.isPresent()) {
            String accessToken = jwtUtil.generateAccessToken(chwOptional.get().getEmail(),chwOptional.get().getCell(),chwOptional.get().getVillage());
            String refreshToken = jwtUtil.generateRefreshToken(chwOptional.get().getEmail(),chwOptional.get().getFullName(),chwOptional.get().getCell(),  chwOptional.get().getVillage());
            addRefreshTokenToCookie(response, refreshToken);
            return ResponseEntity.ok(new LoginResponseDto(true, "Login successful", accessToken,chwOptional.get().getFullName()));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }
    @PostMapping("/createMother")
    public ResponseEntity<String> createMother(@RequestBody Mother mother,HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        Mother savedMother = motherService.saveMother(mother);
        Long total=motherService.getTotalMothers(district, sector);

        updaterService.sendUpdate("total-mothers", total);

        List<Mother> motherList = motherService.getAllMothers(district,sector); // returns List<Mother>

        List<MotherDto> motherDtos = motherList.stream()
                .map(mother1 -> new MotherDto(
                        mother1.getId(),
                        mother1.getNames(),       // maps to motherName in DTO
                        Long.parseLong(mother1.getNationalId()), // convert if needed
                        mother1.getCell(),
                        mother1.getInsurance(),
                        mother1.getStatus()
                ))
                .toList();
        updaterService.sendUpdate("All/mothers", motherDtos);
        return ResponseEntity.ok("Mother created successfully");

    }

    @GetMapping("/allMothers")
    public ResponseEntity<?> getAllMothers(HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        List<Mother> motherList = motherService.getAllMothers(district,sector); // returns List<Mother>

        List<MotherDto> motherDtos = motherList.stream()
                .map(mother -> new MotherDto(
                        mother.getId(),
                        mother.getNames(),       // maps to motherName in DTO
                        Long.parseLong(mother.getNationalId()), // convert if needed
                        mother.getCell(),
                        mother.getInsurance(),
                        mother.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(motherDtos);
    }


    @GetMapping("/totalMothers")
    public ResponseEntity<AmbulanceDto> getTotalMothers(HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        return ResponseEntity.ok(new AmbulanceDto(motherService.getTotalMothers(district,sector)));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMother(@PathVariable Long id, @RequestBody Mother request,HttpServletRequest request1) {
        String district=authService.getUserDistrict(request1);
        String sector=authService.getUserSector(request1);

        boolean success = motherService.updateMother(id, request);
        if (success) {
            List<Mother> motherList = motherService.getAllMothers(district,sector);
            List<MotherDto> motherDtos = motherList.stream()
                    .map(mother -> new MotherDto(
                            mother.getId(),
                            mother.getNames(),       // maps to motherName in DTO
                            Long.parseLong(mother.getNationalId()), // convert if needed
                            mother.getCell(),
                            mother.getInsurance(),
                            mother.getStatus()
                    ))
                    .toList();
            updaterService.sendUpdate("All/mothers", motherDtos);
            return ResponseEntity.ok("Mother updated successfully");
        }

        return ResponseEntity.status(404).body("Mother not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMother(@PathVariable Long id,HttpServletRequest request1) {
        String district=authService.getUserDistrict(request1);
        String sector=authService.getUserSector(request1);
        boolean success = motherService.deleteMother(id);
        if (success) {
            Long total=motherService.getTotalMothers(district, sector);
            updaterService.sendUpdate("total-mothers", total);
            List<Mother> motherList = motherService.getAllMothers(district,sector);
            List<MotherDto> motherDtos = motherList.stream()
                    .map(mother -> new MotherDto(
                            mother.getId(),
                            mother.getNames(),       // maps to motherName in DTO
                            Long.parseLong(mother.getNationalId()), // convert if needed
                            mother.getCell(),
                            mother.getInsurance(),
                            mother.getStatus()
                    ))
                    .toList();
            updaterService.sendUpdate("All/mothers", motherDtos);
            return ResponseEntity.ok("Mother deleted successfully");

        }
        return ResponseEntity.status(404).body("Mother not found");
    }


    public List<NotificationDto> getAllNotifications(String sector) {
        List<Notification> notifications = notificationRepository.findByMotherSectorOrderByCreatedAtDesc(sector);

        // Map each Notification to NotificationDto
        return notifications.stream()
                .map(n -> new NotificationDto(n.getMessage(), n.getCreatedAt()))
                .toList();
    }

    @GetMapping("notifications/today")
    public ResponseEntity<?> getAllNotificationsTodays(HttpServletRequest request) {
        String sector=authService.getUserSector(request);
        return ResponseEntity.ok(notificationService.todaysAppointements(sector));
    }


    @GetMapping("/appointments/upcoming")
    public ResponseEntity<List<AppointementDto>> getUpcomingAppointments() {
        List<AppointementDto> upcomingDtos = appointmentService.getUpcomingAppointments()
                .stream()
                .map(appointment -> new AppointementDto(
                        appointment.getStatus(),
                        appointment.getMother().getNames()

                ))
                .toList();

        return ResponseEntity.ok(upcomingDtos);
    }


}
