package com.project.motherlink2.service;

import com.project.motherlink2.model.Appointments;
import com.project.motherlink2.model.Mother;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final MotherService motherService;
    private final AppointmentService appointmentService;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * MON")
    public void updatePregnancyAndChildrenAppointments() {
        List<Mother> mothers = motherService.getAllMotherUnspecified();

        for (Mother mother : mothers) {

            if (!"With Child".equals(mother.getStatus()) && mother.getPregnancyDays() != null) {
                mother.setPregnancyDays(mother.getPregnancyDays() + 7);


                if (mother.getPregnancyDays() >= 270) {
                    mother.setStatus("With Child");
                    mother.setPregnancyDays(null);
                    mother.setPregnancyMonths(null);
                    mother.setChildrenAgeDays(0);
                } else if (mother.getPregnancyDays() >= 180) {
                    mother.setStatus("Third Trimester");
                } else if (mother.getPregnancyDays() >= 90) {
                    mother.setStatus("Second Trimester");
                } else {
                    mother.setStatus("First Trimester");
                }

                int[] pregnancyMilestones = {90, 180, 270};
                for (int milestone : pregnancyMilestones) {
                    long daysUntilMilestone = milestone - (mother.getPregnancyDays() != null ? mother.getPregnancyDays() : 0);
                    if (daysUntilMilestone == 7) { // 7 days before milestone
                        boolean exists = appointmentService.existsByMotherAndDate(mother, LocalDate.now().plusDays(7));
                        if (!exists) {
                            Appointments appointment = new Appointments();
                            appointment.setMother(mother);
                            appointment.setAppointmentDate(LocalDate.now().plusDays(7));
                            appointment.setStatus("ANC"); // For mother
                            appointmentService.save(appointment);

                            String message = "ANC appointment for mother " + mother.getNames() +
                                    " is scheduled in 7 days.";
                            notificationService.sendNotification(message);
                        }
                    }
                }

                motherService.saveMother(mother);
            }

            if ("With Child".equals(mother.getStatus()) && mother.getChildrenAgeDays() != null) {
                mother.setChildrenAgeDays(mother.getChildrenAgeDays() + 7);

                int[] childMilestones = {30, 60, 90}; // 1,2,3 months in days
                for (int milestone : childMilestones) {
                    long daysUntilMilestone = milestone - mother.getChildrenAgeDays();
                    if (daysUntilMilestone == 7) { // 7 days before milestone
                        boolean exists = appointmentService.existsByMotherAndDate(mother, LocalDate.now().plusDays(7));
                        if (!exists) {
                            Appointments appointment = new Appointments();
                            appointment.setMother(mother);
                            appointment.setAppointmentDate(LocalDate.now().plusDays(7));
                            appointment.setStatus("PNC"); // For child
                            appointmentService.save(appointment);

                            String message = "PNC appointment for child of mother " + mother.getNames() +
                                    " is scheduled in 7 days.";
                            notificationService.sendNotification(message);
                        }
                    }
                }

                motherService.saveMother(mother);
            }
        }
    }


}