package com.project.motherlink2.repository;

import com.project.motherlink2.model.CHW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CHWRepository extends JpaRepository<CHW, Long> {
    List<CHW> findByStatus(String status);

}
