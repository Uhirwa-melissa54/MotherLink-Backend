package com.project.motherlink2.repository;

import com.motherlink.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByOrganizationNameAndDistrictAndSectorAndCell(
            String organizationName, String district, String sector, String cell);
}
