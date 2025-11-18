package com.project.motherlink2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "health_workers")
@AllArgsConstructor
@NoArgsConstructor
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
    private String Sector;
    private String District;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_joined", nullable = false)
    private LocalDate dateJoined;

    @Column(name="status",nullable = false)
    private String status;

    @Column(name="password",nullable = false)
    private String password;






}
