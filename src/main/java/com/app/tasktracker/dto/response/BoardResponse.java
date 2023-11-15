package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {

    private Long boardId;
    private String title;
    private String backGround;
    private boolean isFavorite;
    private Long work_space_id;

    public BoardResponse(Long boardId, String title, String backGround, boolean isFavorite) {
        this.boardId = boardId;
        this.title = title;
        this.backGround = backGround;
        this.isFavorite = isFavorite;
    }

    public BoardResponse(Long boardId, String title, String backGround, Long work_space_id) {
        this.boardId = boardId;
        this.title = title;
        this.backGround = backGround;
        this.work_space_id = work_space_id;
    }
}
