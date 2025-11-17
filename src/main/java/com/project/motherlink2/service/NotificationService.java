package com.project.motherlink2.service;

import com.project.motherlink2.model.Notification;
import com.project.motherlink2.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(String message) {
        Notification n = new Notification();
        n.setMessage(message);
        notificationRepository.save(n);
    }

    public List<Notification> getAllNotifications(String sector) {
        return notificationRepository.findByMotherSectorOrderByCreatedAtDesc(sector);
    }

    public List<Notification> todaysAppointements(String sector) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59) .withNano(999_999_999);
        return notificationRepository.findTodaysNotificationsBySector(sector,startOfDay,endOfDay);
    }


}
