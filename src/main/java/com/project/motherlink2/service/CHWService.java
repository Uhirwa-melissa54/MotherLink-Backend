package com.project.motherlink2.service;

import com.project.motherlink2.model.CHW;
import com.project.motherlink2.repository.CHWRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CHWService {

    private final CHWRepository chwRepository;

    public CHWService(CHWRepository chwRepository) {
        this.chwRepository = chwRepository;
    }

    // Save a new health worker
    public CHW saveCHW(CHW chw) {
        return chwRepository.save(chw);
    }

    // Get all health workers
    public List<CHW> getAllCHWs() {
        return chwRepository.findAll();
    }

    // Get a health worker by ID
    public Optional<CHW> getCHWById(Long id) {
        return chwRepository.findById(id);
    }

    //Get the total number of healthWorkers
    public Long getTotalCHWs() {
        return chwRepository.count();
    }


    public boolean deactivateCHW(Long id) {
        Optional<CHW> chwOptional = chwRepository.findById(id);
        if (chwOptional.isPresent()) {
            CHW chw = chwOptional.get();
            chw.setStatus(false); // 🔴 deactivate
            chwRepository.save(chw);
            return true;
        }
        return false;
    }

    public boolean activateCHW(Long id) {
        Optional<CHW> chwOptional = chwRepository.findById(id);
        if (chwOptional.isPresent()) {
            CHW chw = chwOptional.get();
            chw.setStatus(true); // 🟢 activate
            chwRepository.save(chw);
            return true;
        }
        return false;
    }
}
