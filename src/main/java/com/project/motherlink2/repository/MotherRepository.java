package com.project.motherlink2.repository;

import com.project.motherlink2.model.Mother;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotherRepository extends JpaRepository<Mother, Long> {

    List<Mother> findByDistrictAndSector(String district, String sector);

    long countByDistrictAndSector(String district, String sector);

}
