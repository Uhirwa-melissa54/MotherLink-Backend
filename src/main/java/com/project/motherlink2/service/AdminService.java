package com.project.motherlink2.service;

import com.project.motherlink2.model.Admin;
import com.project.motherlink2.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Optional<Admin> login(String email, String password, String reqPassword) {
        return adminRepository.findByEmailAndPassword(email, password);
    }
}
