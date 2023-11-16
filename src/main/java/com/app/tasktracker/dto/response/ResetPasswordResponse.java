package com.app.tasktracker.dto.response;

import lombok.Builder;
import com.app.tasktracker.enums.Role;

@Builder
public record ResetPasswordResponse(
        Long userId,
        String firstName,
        String lastName,
        String email,
        Role role,
        String jwtToken,
        String message
) {
}