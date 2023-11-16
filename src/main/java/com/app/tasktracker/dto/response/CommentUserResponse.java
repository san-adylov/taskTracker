package com.app.tasktracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUserResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String image;
}