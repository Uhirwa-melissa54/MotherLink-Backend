package com.project.motherlink2.service;

import com.project.motherlink2.model.Appointments;
import com.project.motherlink2.model.Mother;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulerService {

    private final MotherService motherService;
    private final AppointmentService appointmentService;
    private final NotificationService notificationService;

    public SchedulerService(MotherService motherService,
                            AppointmentService appointmentService,
                            NotificationService notificationService) {
        this.motherService = motherService;
        this.appointmentService = appointmentService;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void createAppointmentsAndNotify() {
        List<Mother> mothers = motherService.getAllMothers();

        for (Mother mother : mothers) {

            int pregnancyDays = mother.getPregnancyDays();
            int[] milestones = {90, 180, 270};

            for (int milestone : milestones) {
                long daysUntilMilestone = milestone - pregnancyDays;

                if (daysUntilMilestone == 7) {
                    boolean exists = appointmentService.existsByMotherAndDate(mother, LocalDate.now().plusDays(7));
                    if (!exists) {
                        Appointments appointment = new Appointments();
                        appointment.setMother(mother);
                        appointment.setAppointmentDate(LocalDate.now().plusDays(7));
                        appointment.setStatus("Upcoming");
                        appointmentService.save(appointment);

                        String message = "Appointment for mother " + mother.getNames() +
                                " is upcoming in 7 days.";
                        notificationService.sendNotification(message);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void updatePregnancyProgressWeekly() {
        List<Mother> mothers = motherService.getAllMothers();
        for (Mother mother : mothers) {
            mother.setPregnancyDays(mother.getPregnancyDays() + 7);

            if (mother.getPregnancyDays() >= 270) mother.setStatus("Delivery Soon");
            else if (mother.getPregnancyDays() >= 180) mother.setStatus("Third Trimester");
            else if (mother.getPregnancyDays() >= 90) mother.setStatus("Second Trimester");
            else mother.setStatus("First Trimester");

            motherService.saveMother(mother);
        }
    }
}
