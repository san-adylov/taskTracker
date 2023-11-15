package com.app.tasktracker.services;

import com.app.tasktracker.dto.response.NotificationResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getNotificationById(Long notificationId);

    SimpleResponse markAsRead();


}