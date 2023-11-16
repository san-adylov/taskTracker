package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;

    public UserResponse(Long userId, String firstName, String lastName, String email, String avatar) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
    }
}
