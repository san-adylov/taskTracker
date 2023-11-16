package com.app.tasktracker.dto.request;

import jakarta.persistence.Column;
import com.app.tasktracker.validation.EmailValid;
import com.app.tasktracker.validation.PasswordValid;

public record SignInRequest(
        
        @Column(unique = true)
        @EmailValid
        String email,
        
        @PasswordValid
        String password
) {
}
