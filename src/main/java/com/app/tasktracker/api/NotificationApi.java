package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.response.NotificationResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN','MEMBER')")
@Tag(name = "Notification API", description = "API for managing notifications")
public class NotificationApi {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get all", description = "Get all notifications")
    public List<NotificationResponse> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{notificationId}")
    @Operation(summary = "Get by id", description = "Get notification by notification id")
    public NotificationResponse getById(@PathVariable Long notificationId) {
        return notificationService.getNotificationById(notificationId);
    }

    @PutMapping
    @Operation(summary = "Mark notifications as read", description = "Marks all user notifications as read.")
    public SimpleResponse markAsRead() {
        return notificationService.markAsRead();
    }
}
