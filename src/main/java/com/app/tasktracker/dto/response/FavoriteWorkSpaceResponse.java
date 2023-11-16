package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteWorkSpaceResponse {

    private Long userId;
    private Long workSpaceId;
    private String name;
    private boolean isFavorite;

    public FavoriteWorkSpaceResponse(Long userId, Long workSpaceId, String name) {
        this.userId = userId;
        this.workSpaceId = workSpaceId;
        this.name = name;
    }
}
