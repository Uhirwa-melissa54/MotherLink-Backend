package com.project.motherlink2.Dtos;

import com.project.motherlink2.model.Admin;

public class LoginResponseDto {
    private boolean success;
    private String message;
    private Admin Admin;

    public  LoginResponseDto(boolean success, String message, Admin Admin) {
        this.success = success;
        this.message = message;
        this.Admin = Admin;
    }
}
