package com.project.motherlink2.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotificationDto {
    private String message;

    private LocalDateTime createdAt;

}
