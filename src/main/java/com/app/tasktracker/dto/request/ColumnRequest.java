package com.app.tasktracker.dto.request;

import lombok.Builder;

@Builder
public record ColumnRequest(
        Long boardId,
        String title
){
}
