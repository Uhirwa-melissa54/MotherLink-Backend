package com.project.motherlink2.Dtos;

import com.project.motherlink2.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterResponseDto {
    private boolean success;
    private String message;
    private String  accessToken;
    private String  name;



}
