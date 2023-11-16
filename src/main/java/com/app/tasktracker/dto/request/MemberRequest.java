package com.app.tasktracker.dto.request;

import lombok.Builder;

@Builder
public record MemberRequest(
        String email
) {
}
