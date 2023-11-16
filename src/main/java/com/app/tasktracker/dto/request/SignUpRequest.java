package com.app.tasktracker.dto.request;

public record SignUpRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
