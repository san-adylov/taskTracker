package com.app.tasktracker.dto.request;

import com.app.tasktracker.enums.Role;

public record ParticipantsChangeRequest(
        Long memberId,
        Long workSpacesId,
        Role role
) {
}
