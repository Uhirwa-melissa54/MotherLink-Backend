package com.project.motherlink2.repository;

import com.project.motherlink2.model.Mother;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotherRepository extends JpaRepository<Mother, Long> {
    
}
