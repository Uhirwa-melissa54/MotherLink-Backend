package com.project.motherlink2.repository;

import com.project.motherlink2.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrganizationRepository<organizations> extends JpaRepository<Organization, Integer> {
//    Optional<Organization> findByOrganizationNameAndDistrictAndSectorAndCell(
//            String organizationName, String district, String sector, String cell); 
}
