package com.project.motherlink2.repository;

import com.project.motherlink2.model.CHW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CHWRepository extends JpaRepository<CHW, Long> {

}
