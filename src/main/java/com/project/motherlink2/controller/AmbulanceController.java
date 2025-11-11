package com.project.motherlink2.controller;

import com.project.motherlink2.model.Ambulance;
import com.project.motherlink2.model.Organization;
import com.project.motherlink2.service.AmbulanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambulances")
@CrossOrigin(origins = "*")
public class AmbulanceController {
    private final AmbulanceService ambulanceService;

    public AmbulanceController(AmbulanceService ambulanceService) {
        this.ambulanceService = ambulanceService;
    }

    @GetMapping("/totalAmbulance")

    @PostMapping("/create")
    public Ambulance createAmbulance(@RequestBody Ambulance ambulance) {
        return ambulanceService.saveAmbulance(ambulance);
    }


}
