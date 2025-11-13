package com.project.motherlink2.service;


import com.project.motherlink2.model.Mother;
import com.project.motherlink2.repository.MotherRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class MotherService {
    private final MotherRepository motherRepository;
    public Mother saveMother(Mother mother) {
        return motherRepository.save(mother);
    }
}
