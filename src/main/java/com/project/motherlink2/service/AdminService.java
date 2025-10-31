package com.project.motherlink2.service;

import com.project.motherlink2.model.Admin;
import com.project.motherlink2.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    public Optional<Admin> exists(String fullName,String email, Long organizationId) {
        return adminRepository.findByFullNameAndEmailAndOrganization_Id(fullName,email,organizationId);
    }

    public Optional<Admin> login(String email, String rawPassword) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(rawPassword, admin.getPassword())) {
                return Optional.of(admin);
            }
        }
        return Optional.empty();
    }
    public Admin save(Admin admin) {
        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);
        return adminRepository.save(admin);
    }


}
