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
    public Optional<Admin> exists(String fullName,String email, Long idcd ) {
        return adminRepository.findByFullNameAndEmailAndOrganizationdId(fullName,email,id);
    }

    public Optional<Admin> login(String email, String password, String reqPassword) {
        return adminRepository.findByEmailAndPassword(email, password);
    }
    public Admin save(Admin admin) {
        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);
        return adminRepository.save(admin);
    }
}
