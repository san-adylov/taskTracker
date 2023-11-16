package com.app.tasktracker.dto.request;

public record UpdateCardRequest(
        Long cardId,
        String title,
        String description
) {
}