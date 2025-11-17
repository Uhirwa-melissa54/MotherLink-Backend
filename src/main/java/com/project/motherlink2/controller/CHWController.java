package com.project.motherlink2.controller;

import com.project.motherlink2.Dtos.AmbulanceDto;
import com.project.motherlink2.Dtos.CHWDto;
import com.project.motherlink2.model.CHW;
import com.project.motherlink2.service.AuthService;
import com.project.motherlink2.service.CHWService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthworkers")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CHWController {

    private final CHWService chwService;
    private final AuthService authService;


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
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        List<CHW> chwList = chwService.getAllCHWs(district, sector);

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
    public ResponseEntity<Long> getTotalCHWs(HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        Long count = chwService.getTotalCHWs(district,sector);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/activeCHW")
    public ResponseEntity<AmbulanceDto> getActiveCHWs(HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        Long count = chwService.getActiveCHWCount(district,sector);
        return ResponseEntity.status(HttpStatus.OK).body(new AmbulanceDto(count));
    }
    @GetMapping("/inactiveCHW")
    public ResponseEntity<AmbulanceDto> getInaActiveCHWs(HttpServletRequest request) {
        String district=authService.getUserDistrict(request);
        String sector=authService.getUserSector(request);
        Long count = chwService.getInActiveCHWCount(district,sector);
        return ResponseEntity.status(HttpStatus.OK).body(new AmbulanceDto(count));
    }





}
