package com.project.motherlink2.repository;

import com.project.motherlink2.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Find admin by email and password
    Optional<Admin> findByEmailAndPassword(String email, String password);
}
