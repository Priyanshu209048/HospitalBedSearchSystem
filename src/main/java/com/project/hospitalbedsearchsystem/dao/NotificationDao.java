package com.project.hospitalbedsearchsystem.dao;

import com.project.hospitalbedsearchsystem.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDao extends JpaRepository<Notification, Integer> {
}
