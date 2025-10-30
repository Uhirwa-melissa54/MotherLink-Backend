package com.project.motherlink2.service;

import com.project.motherlink2.model.Admin;
import com.project.motherlink2.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    public Optional<Admin> exists(String fullName,String email, Long organizationId) {
        return adminRepository.findByFullNameAndEmailAndOrganizationdId(fullName,email,organizationId);
    }

    public Optional<Admin> login(String email, String password, String reqPassword) {
        return adminRepository.findByEmailAndPassword(email, password);
    }
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
