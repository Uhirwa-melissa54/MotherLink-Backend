package com.project.motherlink2.model;

import jakarta.persistence.*;
import lombok.Data;
import com.project.motherlink2.model.Organization;


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
    public Admin(String fullName, String email, Object o, String position, Object o1, Organization organization) {

    }

    public Admin() {

    }
}
