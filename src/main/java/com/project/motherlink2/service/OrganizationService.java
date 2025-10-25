package com.project.motherlink2.service;

import com.project.motherlink2.model.Organization;
import com.project.motherlink2.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization saveOrganization(Organization organization) {
//        Optional<Organization> existing = organizationRepository
//                .findByOrganizationNameAndDistrictAndSectorAndCell(
//                        organization.getOrganizationName(),
//                        organization.getDistrict(),
//                        organization.getSector(),
//                        organization.getCell()
//                );
//
//        if (existing.isPresent()) {
//            return existing.get(); // Don’t create duplicate
//        }
        return (Organization) organizationRepository.save(organization);
    }

    public List<Organization> getOrganizations() {
        return organizationRepository.findAll();
    }

    public String getOrganizationById(Long organizationId) {
        organizationRepository.findById(organizationId);
        return "Organization created successfully ";
    }
}
