package com.project.motherlink2.repository;

import com.project.motherlink2.model.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    List<Ambulance> findByIsAvailableTrue();

    List<Ambulance> findByIsAvailableFalse();

}
