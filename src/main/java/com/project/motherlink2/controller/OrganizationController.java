package com.project.motherlink2.controller;

import com.project.motherlink2.model.Organization;
import com.project.motherlink2.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@CrossOrigin(origins = "*")
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
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.saveOrganization(organization);
    }
//    @GetMapping("/{/id}")
//
//    public String getOrganizationById(@RequestParam Long id) {
//        return organizationService.getOrganizationById(id);
//    }
}
