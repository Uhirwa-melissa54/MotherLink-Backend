package com.motherlink.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String position;
    private String password;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
