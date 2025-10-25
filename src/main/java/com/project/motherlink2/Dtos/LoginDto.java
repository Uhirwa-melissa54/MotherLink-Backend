package com.project.motherlink2.Dtos;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
    private String position;
}