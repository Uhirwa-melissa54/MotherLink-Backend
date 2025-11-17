package com.project.motherlink2.service;

import com.project.motherlink2.model.Notification;
import com.project.motherlink2.repository.NotificationRepository;
import org.springframework.stereotype.Service;

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
}
