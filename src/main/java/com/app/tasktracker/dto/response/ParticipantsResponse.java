package com.app.tasktracker.dto.response;

import lombok.*;
import com.app.tasktracker.enums.Role;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String image;
    private Role role;
    private Boolean isAdmin;

    public ParticipantsResponse(Long userId, String fullName, String email, Role role,Boolean isAdmin) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isAdmin=isAdmin;
    }
}