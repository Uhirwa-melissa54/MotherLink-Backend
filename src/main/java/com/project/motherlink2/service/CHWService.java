package com.project.motherlink2.service;

import com.project.motherlink2.model.Admin;
import com.project.motherlink2.model.CHW;
import com.project.motherlink2.repository.CHWRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CHWService {

    private final CHWRepository chwRepository;
    private final PasswordEncoder passwordEncoder;




    public CHW saveCHW(CHW chw) {
        String hashedPassword = passwordEncoder.encode(chw.getPassword());
        chw.setPassword(hashedPassword);

        return chwRepository.save(chw);
    }
    public Optional<CHW> login(String email, String rawPassword) {
        Optional<CHW> CHWOptional = chwRepository.findByEmail(email);
        if (CHWOptional.isPresent()) {
            CHW chw = CHWOptional.get();
            if (passwordEncoder.matches(rawPassword, chw.getPassword())) {
                return Optional.of(chw);
            }
        }
        return Optional.empty();
    }

    public List<CHW> getAllCHWs(String district, String sector) {

        return chwRepository.findByDistrictAndSector(district, sector);
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

    public long getInActiveCHWCount() {
        List<CHW> activeCHWs = chwRepository.findByStatus("inactive");
        long numberOfActiveCHWs = activeCHWs.size();
        return numberOfActiveCHWs;
    }
}
