package com.project.motherlink2.service;

import com.project.motherlink2.model.Ambulance;
import com.project.motherlink2.repository.AmbulanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmbulanceService {
    private final AmbulanceRepository ambulanceRepository;

    public AmbulanceService(AmbulanceRepository ambulanceRepository) {
        this.ambulanceRepository = ambulanceRepository;
    }

    public Ambulance saveAmbulance(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }

    public List<Ambulance> getAmbulances() {
        return  ambulanceRepository.findAll();
    }
}
