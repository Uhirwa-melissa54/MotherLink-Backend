package com.motherlink.service;

import com.motherlink.model.Ambulance;
import com.project.motherlink2.repository.AmbulanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AmbulanceService {
    private final AmbulanceRepository ambulanceRepository;

    public AmbulanceService(AmbulanceRepository ambulanceRepository) {
        this.ambulanceRepository = ambulanceRepository;
    }

    public Ambulance saveAmbulance(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }
}
