package com.project.motherlink2.repository;


import com.project.motherlink2.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.appointment.mother.sector = :sector ORDER BY n.createdAt DESC")
    List<Notification> findByMotherSectorOrderByCreatedAtDesc(@Param("sector") String sector);

    @Query("SELECT n FROM Notification n " +
            "WHERE n.appointment.mother.sector = :sector " +
            "AND n.createdAt >= :startOfDay AND n.createdAt <= :endOfDay " +
            "ORDER BY n.createdAt DESC")
    List<Notification> findTodaysNotificationsBySector(
            @Param("sector") String sector,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );



}
