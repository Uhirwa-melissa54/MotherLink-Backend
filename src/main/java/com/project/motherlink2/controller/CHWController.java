package com.project.motherlink2.controller;

import com.project.motherlink2.model.CHW;
import com.project.motherlink2.service.CHWService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthworkers")
@CrossOrigin(origins = "*")
public class CHWController {

    private final CHWService chwService;

    public CHWController(CHWService chwService) {
        this.chwService = chwService;
    }

    @PostMapping("/create")
    public ResponseEntity<CHW> createCHW(@RequestBody CHW chw) {
        CHW savedCHW = chwService.saveCHW(chw);
        return ResponseEntity.ok(savedCHW);
    }

    @GetMapping
    public ResponseEntity<List<CHW>> getAllCHWs() {
        return ResponseEntity.ok(chwService.getAllCHWs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCHWById(@PathVariable Long id) {
        return chwService.getCHWById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Health worker not found"));
    }

    @GetMapping("/totalCHW")
    public ResponseEntity<Long> getTotalCHWs() {
        Long count = chwService.getTotalCHWs();
        return ResponseEntity.ok(count);
    }


}
