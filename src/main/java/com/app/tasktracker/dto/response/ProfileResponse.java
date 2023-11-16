package com.app.tasktracker.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfileResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private int countWorkSpaces;
    private List<WorkSpaceResponse> workSpaceResponse;

    public ProfileResponse(Long userId, String firstName, String lastName, String email, String avatar, int countWorkSpaces) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.countWorkSpaces = countWorkSpaces;
    }
}