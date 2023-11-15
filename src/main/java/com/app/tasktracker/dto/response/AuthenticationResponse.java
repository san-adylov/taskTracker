package com.app.tasktracker.dto.response;

import lombok.Builder;
import com.app.tasktracker.enums.Role;

@Builder
public record AuthenticationResponse(
        String token,
        String email,
        Role role
) {
}
