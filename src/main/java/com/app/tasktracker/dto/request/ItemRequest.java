package com.app.tasktracker.dto.request;

public record ItemRequest(
        Long checkListId,
        String title
) {
}