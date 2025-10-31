package com.project.motherlink2.repository;

import com.project.motherlink2.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByFullNameAndEmailAndOrganization_Id(String fullName, String email, Long organizationId);


    Optional<Admin> findByEmail(String email);
}
