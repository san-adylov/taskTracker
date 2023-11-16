package com.app.tasktracker.dto.request;

public record ResetPasswordRequest(
        Long userId,
        String newPassword,
        String repeatPassword) {
}
