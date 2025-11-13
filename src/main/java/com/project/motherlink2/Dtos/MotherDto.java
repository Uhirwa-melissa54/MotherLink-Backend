package com.project.motherlink2.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotherDto {
   private long id;
    private String motherName;
    private long nationalId;
    private String cell;
    private String insurance;
    private String status;
}
