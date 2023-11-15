package com.app.tasktracker.dto.request;

import lombok.Builder;

@Builder
public record AttachmentRequest(
        String documentLink,
        Long cardId
) {
}
