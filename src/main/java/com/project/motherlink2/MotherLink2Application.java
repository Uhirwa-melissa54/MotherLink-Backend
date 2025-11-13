package com.project.motherlink2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MotherLink2Application {
    public static void main(String[] args) {
        SpringApplication.run(MotherLink2Application.class, args);
    }
}
