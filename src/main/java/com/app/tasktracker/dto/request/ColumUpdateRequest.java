package com.app.tasktracker.dto.request;

import lombok.Builder;

@Builder
public record ColumUpdateRequest(
        Long columId,
        String newTitle
) {
}
