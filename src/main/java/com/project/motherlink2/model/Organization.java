package com.project.motherlink2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    private String licenseNumber; // Now the primary key

    private String name;
    private String district;
    private String sector;
    private String cell;
    private String physical_address;
    private String type_of_facility;
}
