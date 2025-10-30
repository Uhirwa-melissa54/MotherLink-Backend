package com.project.motherlink2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.project.motherlink2.model.Organization;

@AllArgsConstructor
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

    public Admin() {

    }
}
