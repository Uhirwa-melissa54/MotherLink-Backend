package com.project.motherlink2.service;

import com.project.motherlink2.model.Mother;
import com.project.motherlink2.repository.MotherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MotherService {

    private final MotherRepository motherRepository;

    public Mother saveMother(Mother mother) {
        return motherRepository.save(mother);
    }

    public List<Mother> getAllMothers() {
        return motherRepository.findAll();
    }

    public Optional<Mother> getMotherById(Long id) {
        return motherRepository.findById(id);
    }

    public void deleteMother(Long id) {
        motherRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return motherRepository.existsById(id);
    }
}
