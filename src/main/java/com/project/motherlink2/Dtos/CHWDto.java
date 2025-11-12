package com.project.motherlink2.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CHWDto {
    String name;
    String gender;
    String phoneNumber;
    LocalDate startDate;
    String district;
    String status;


}
