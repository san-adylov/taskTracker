package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.response.NotificationResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.Notification;
import com.app.tasktracker.models.User;
import com.app.tasktracker.repositories.NotificationRepository;
import com.app.tasktracker.repositories.customRepository.CustomNotificationRepository;
import com.app.tasktracker.services.NotificationService;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final CustomNotificationRepository customNotificationRepository;
    private final JwtService jwtService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<NotificationResponse> getAllNotifications() {
        return customNotificationRepository.getAllNotifications();
    }

    @Override
    public NotificationResponse getNotificationById(Long notificationId) {
        User user = jwtService.getAuthentication();

        Long l = jdbcTemplate.queryForObject("""
                        SELECT n.id FROM notifications n
                        JOIN notifications_members nm ON n.id = nm.notifications_id
                        WHERE n.id = ? AND nm.members_id = ?
                        """, Long.class
                , notificationId
                , user.getId());

        Notification notification = notificationRepository.findById(l).orElseThrow(() -> new NotFoundException("Notification with id: " + notificationId + "is not found"));
        boolean isRead = notification.getIsRead();

        if (!isRead) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
            log.info("Mark as read successfully👍");
        }

        return customNotificationRepository.getNotificationById(notificationId);
    }


    @Override
    public SimpleResponse markAsRead() {
        User user = jwtService.getAuthentication();
        List<Notification> notifications = notificationRepository.getAllNotification(user.getId());
        for (Notification notification : notifications) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Mark as read successfully👍")
                .build();
    }

}
