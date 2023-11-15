package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.NotificationResponse;

import java.util.List;

public interface CustomNotificationRepository {

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getNotificationById(Long notificationId);


}