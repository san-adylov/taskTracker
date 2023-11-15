package com.app.tasktracker.dto.request;

import lombok.Builder;

@Builder
public record BoardUpdateRequest(
        Long boardI,
        String title,
        String backGround
) {
}
