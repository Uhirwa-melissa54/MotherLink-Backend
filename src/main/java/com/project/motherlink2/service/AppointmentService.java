package com.project.motherlink2.service;

import com.project.motherlink2.model.Appointments;
import com.project.motherlink2.model.Mother;
import com.project.motherlink2.repository.AppointmentsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public boolean existsByMotherAndDate(Mother mother, LocalDate date) {
        return appointmentRepository.findAll().stream()
                .anyMatch(a -> a.getMother().getId().equals(mother.getId())
                        && a.getAppointmentDate().equals(date));
    }
    public List<Appointments> getUpcomingAppointments() {
        return appointmentRepository.findByStatus("Upcoming");
    }
}
