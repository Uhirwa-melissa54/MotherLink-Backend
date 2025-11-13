package com.project.motherlink2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mothers")
public class Mother {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // works with PostgreSQL BIGSERIAL
    private Long id;

    private String names;

    private LocalDate dob;

    private String phone;

    private String nationalId;

    private String maritalStatus;

    private String district;

    private String sector;

    private String cell;

    private String village;

    private Integer pregnancyMonths;

    private Integer pregnancyDays;

    private String status;

    private LocalDate deliveryDate;

    private String healthCenter;

    private String insurance;
}