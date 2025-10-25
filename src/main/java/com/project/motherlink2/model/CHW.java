package com.project.motherlink2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "health_workers")
public class CHW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Internal primary key

    @Column(name = "chw_id", nullable = false, unique = true)
    private String chwId;  // Community Health Worker ID

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String gender;

    @Column(unique = true)
    private String email;

    @Column(name = "national_id", unique = true)
    private String nationalId;

    private String cell;
    private String village;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_joined", nullable = false)
    private LocalDate dateJoined;

    @Column(name="status",nullable = false)
    private String status;

    // Default constructor
    public CHW() {}

    // Optional constructor for convenience
    public CHW(String chwId, String fullName, String gender, String email,
                        String nationalId, String cell, String village,
                        String phoneNumber, LocalDate dateJoined,String status) {
        this.chwId = chwId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.nationalId = nationalId;
        this.cell = cell;
        this.village = village;
        this.phoneNumber = phoneNumber;
        this.dateJoined = dateJoined;
        this.status = status;
    }
}
