package com.app.tasktracker.dto.request;

import lombok.*;
import com.app.tasktracker.enums.Role;

@Builder
public record ParticipantsRequest(
        Long workSpacesId,
        String email,
        String link,
        Role role
) {
}
