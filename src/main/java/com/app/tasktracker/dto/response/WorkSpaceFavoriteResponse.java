package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceFavoriteResponse {

    private Long workSpaceId;
    private String name;
    private boolean isFavorite;
}