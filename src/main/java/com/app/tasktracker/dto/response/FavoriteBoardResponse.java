package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteBoardResponse {

    private Long userId;
    private Long boardId;
    private String title;
    private String backGround;
    private boolean isFavorite;

    public FavoriteBoardResponse(Long userId, Long boardId, String title, String backGround) {
        this.userId = userId;
        this.boardId = boardId;
        this.title = title;
        this.backGround = backGround;
    }
}