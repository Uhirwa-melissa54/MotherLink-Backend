package com.project.motherlink2.service;

import com.project.motherlink2.model.Appointments;
import com.project.motherlink2.repository.AppointmentsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class AppointmentService {
    private final AppointmentsRepository appointmentRepository;



    public Appointments save(Appointments appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointments> getAll() {
        return appointmentRepository.findAll();
    }
}
