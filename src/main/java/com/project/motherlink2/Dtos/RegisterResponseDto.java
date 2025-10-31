package com.project.motherlink2.Dtos;

import com.project.motherlink2.model.Admin;

public class RegisterResponseDto {
    private boolean success;
    private String message;
    private String  accessToken;


    public  RegisterResponseDto(boolean success, String message, String accessToken) {
        this.success = success;
        this.message = message;
        this.accessToken = accessToken;
    }
}
