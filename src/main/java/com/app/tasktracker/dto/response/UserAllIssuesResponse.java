package com.app.tasktracker.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserAllIssuesResponse {

    private Long userId;
    private String fullName;
    private String image;
    private String email;

    public UserAllIssuesResponse(Long userId, String fullName, String image, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.image = image;
        this.email = email;
    }
}
