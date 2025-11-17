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
    public long getTotalMothers(String district, String sector) {
        return motherRepository.countByDistrictAndSector(district, sector);
    }

    public boolean updateMother(Long id, Mother request) {
        Optional<Mother> motherOptional = motherRepository.findById(id);
        if (motherOptional.isPresent()) {
            Mother existingMother = motherOptional.get();

            existingMother.setNames(request.getNames());
            existingMother.setDob(request.getDob());
            existingMother.setPhone(request.getPhone());
            existingMother.setNationalId(request.getNationalId());
            existingMother.setMaritalStatus(request.getMaritalStatus());
            existingMother.setDistrict(request.getDistrict());
            existingMother.setSector(request.getSector());
            existingMother.setCell(request.getCell());
            existingMother.setVillage(request.getVillage());
            existingMother.setPregnancyMonths(request.getPregnancyMonths());
            existingMother.setPregnancyDays(request.getPregnancyDays());
            existingMother.setStatus(request.getStatus());
            existingMother.setDeliveryDate(request.getDeliveryDate());
            existingMother.setHealthCenter(request.getHealthCenter());
            existingMother.setInsurance(request.getInsurance());

            motherRepository.save(existingMother);
            return true;
        }
        return false;
    }



    public List<Mother> getAllMothers(String district, String sector) {
    return motherRepository.findByDistrictAndSector(district,sector);
    }

    public Optional<Mother> getMotherById(Long id) {
        return motherRepository.findById(id);
    }

    public boolean deleteMother(Long id) {
        if (motherRepository.existsById(id)) {
            motherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Long id) {
        return motherRepository.existsById(id);
    }
}
