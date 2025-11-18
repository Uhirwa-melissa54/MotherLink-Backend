package com.project.motherlink2.repository;

import com.project.motherlink2.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {
    List<Appointments> findByStatus(String status);
    List<Appointments> findByDistrictAndSectorAndType(String district, String sector, String type);
    long countByDistrictAndSectorAndType(String district, String sector, String type);


}
