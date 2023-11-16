package com.app.tasktracker.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import com.app.tasktracker.validation.EmailValid;

@Builder
public record UserRequest(
        String firstName,
        String lastName,

        @Column(unique = true)
        @EmailValid
        String email,
        String password
) {
}