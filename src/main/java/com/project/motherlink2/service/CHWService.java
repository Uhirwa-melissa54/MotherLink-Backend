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


    public CHW saveCHW(CHW chw) {
        return chwRepository.save(chw);
    }


    public List<CHW> getAllCHWs() {
        return chwRepository.findAll();
    }


    public Optional<CHW> getCHWById(Long id) {
        return chwRepository.findById(id);
    }


    public Long getTotalCHWs() {
        return chwRepository.count();
    }


    public boolean deactivateCHW(Long id) {
        Optional<CHW> chwOptional = chwRepository.findById(id);
        if (chwOptional.isPresent()) {
            CHW chw = chwOptional.get();
            chw.setStatus("inactive");
            chwRepository.save(chw);
            return true;
        }
        return false;
    }

    public boolean updateCHW(Long id, CHW updatedCHW) {
        Optional<CHW> chwOptional = chwRepository.findById(id);
        if (chwOptional.isPresent()) {
            CHW existingCHW = chwOptional.get();


            existingCHW.setChwId(updatedCHW.getChwId());
            existingCHW.setFullName(updatedCHW.getFullName());
            existingCHW.setGender(updatedCHW.getGender());
            existingCHW.setEmail(updatedCHW.getEmail());
            existingCHW.setNationalId(updatedCHW.getNationalId());
            existingCHW.setCell(updatedCHW.getCell());
            existingCHW.setVillage(updatedCHW.getVillage());
            existingCHW.setPhoneNumber(updatedCHW.getPhoneNumber());
            existingCHW.setDateJoined(updatedCHW.getDateJoined());
            existingCHW.setStatus(updatedCHW.getStatus());

            chwRepository.save(existingCHW);
            return true;
        }
        return false;
    }





    public boolean activateCHW(Long id) {
        Optional<CHW> chwOptional = chwRepository.findById(id);
        if (chwOptional.isPresent()) {
            CHW chw = chwOptional.get();
            chw.setStatus("active");
            chwRepository.save(chw);
            return true;
        }
        return false;
    }

    public long getActiveCHWCount() {
        List<CHW> activeCHWs = chwRepository.findByStatus("active");
        long numberOfActiveCHWs = activeCHWs.size();
        return numberOfActiveCHWs;
    }
}
