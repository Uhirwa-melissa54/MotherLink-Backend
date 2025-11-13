package com.project.motherlink2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ambulances")
public class Ambulance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dhis2FacilityCode;
    private String dispatchLink;
    private int numberOfAmbulances;
    private String phoneNumber;
    @Column(nullable = true)
    private boolean isAvailable;
    private String emergencyPhoneNumber;

    @OneToOne
    @JoinColumn(name = "license_number")
    private Organization organization;
}
