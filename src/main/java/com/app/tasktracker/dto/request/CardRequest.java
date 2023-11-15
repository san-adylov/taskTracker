package com.app.tasktracker.dto.request;

public record CardRequest(
        Long columnId,
        String title,
        String description
) {
}