package com.motherlink.model;

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
    private String emergencyPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
