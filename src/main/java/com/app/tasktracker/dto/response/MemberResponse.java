package com.app.tasktracker.dto.response;

import lombok.*;
import com.app.tasktracker.enums.Role;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private Long userId;
    private String firstName;
    private String LastName;
    private String email;
    private String image;
    private Role role;
}