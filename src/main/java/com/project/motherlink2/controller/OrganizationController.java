package com.project.motherlink2.controller;

import com.project.motherlink2.model.Organization;
import com.project.motherlink2.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    
    @GetMapping
 public List<Organization> getOrganizations() {
        return organizationService.getOrganizations();
 }
    @PostMapping("/create")
    public ResponseEntity<String> createOrganization(@RequestBody Organization organization) {
        try {
            organizationService.saveOrganization(organization);
            return ResponseEntity.ok("Organization created");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }
//    @GetMapping("/{/id}")
//
//    public String getOrganizationById(@RequestParam Long id) {
//        return organizationService.getOrganizationById(id);
//    }
}
