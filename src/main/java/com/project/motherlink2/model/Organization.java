package com.motherlink.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String organizationName;
    private String district;
    private String sector;
    private String cell;
    private String physicalAddress;
    private String facility;
    private String licenseNumber;
}
