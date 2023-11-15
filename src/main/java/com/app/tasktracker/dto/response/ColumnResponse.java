package com.app.tasktracker.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ColumnResponse {

    private Long columnId;
    private String title;
    private Boolean isArchive;
    private Long boardId;
    private List<CardResponse> cardResponses;

    public ColumnResponse(Long columnId, String title, Boolean isArchive) {

        this.columnId = columnId;
        this.title = title;
        this.isArchive=isArchive;
    }

    public ColumnResponse(Long columnId, String title, Boolean isArchive, Long boardId, List<CardResponse> cardResponses) {
        this.columnId = columnId;
        this.title = title;
        this.isArchive = isArchive;
        this.boardId = boardId;
        this.cardResponses = cardResponses;

    }
}
