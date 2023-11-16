package com.app.tasktracker.dto.request;

import lombok.*;
import com.app.tasktracker.enums.Role;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteRequest {
    private String email;
    private Role role;
    private String link;
    private Long boardId;

}
