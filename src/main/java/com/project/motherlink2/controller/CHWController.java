package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.AmbulanceDto;
import com.project.motherlink2.Dtos.CHWDto;
import com.project.motherlink2.model.CHW;
import com.project.motherlink2.service.AuthService;
import com.project.motherlink2.service.CHWService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthworkers")
@CrossOrigin(origins = "*")
public class CHWController {

    private final CHWService chwService;
    private final AuthService authService;

    public CHWController(CHWService chwService) {
        this.chwService = chwService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCHW(@RequestBody CHW chw) {
        CHW savedCHW = chwService.saveCHW(chw);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping
    public ResponseEntity<List<CHW>> getAllCHWs(HttpServletRequest request) {
        String district = authService.getUserDistrict(request);
        String sector = authService.getUserSector(request);
        return ResponseEntity.ok(chwService.getAllCHWs(district, sector));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCHWById(@PathVariable Long id) {
        return chwService.getCHWById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Health worker not found"));
    }
    @GetMapping("/allCHW")
    public ResponseEntity<?> getAllCHW(HttpServletRequest request) {
        String district=
        List<CHW> chwList = chwService.getAllCHWs();

        List<CHWDto> chwDtos = chwList.stream()
                .map(chw -> new CHWDto(
                        chw.getFullName(),
                        chw.getGender(),
                        chw.getPhoneNumber(),
                        chw.getDateJoined(),
                        chw.getVillage(),
                        chw.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(chwDtos);
    }
    @GetMapping("/totalCHW")
    public ResponseEntity<Long> getTotalCHWs() {
        Long count = chwService.getTotalCHWs();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/activeCHW")
    public ResponseEntity<AmbulanceDto> getActiveCHWs() {
        Long count = chwService.getActiveCHWCount();
        return ResponseEntity.status(HttpStatus.OK).body(new AmbulanceDto(count));
    }
    @GetMapping("/inactiveCHW")
    public ResponseEntity<AmbulanceDto> getInaActiveCHWs() {
        Long count = chwService.getInActiveCHWCount();
        return ResponseEntity.status(HttpStatus.OK).body(new AmbulanceDto(count));
    }





}
