package com.app.tasktracker.dto.request;

public record LabelRequest(
        String description,
        String color
) {
}
