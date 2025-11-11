package com.project.motherlink2.service;

import com.project.motherlink2.model.Ambulance;
import com.project.motherlink2.repository.AmbulanceRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class AmbulanceService {
    private final AmbulanceRepository ambulanceRepository;
    public Ambulance saveAmbulance(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }
    public long getTotalAmbulance() {
        return ambulanceRepository.count();
    }
}
