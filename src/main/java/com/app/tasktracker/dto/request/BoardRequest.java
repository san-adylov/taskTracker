package com.app.tasktracker.dto.request;

import lombok.*;


@Builder
public record BoardRequest (
        String title,
        String backGround,
        Long workSpaceId
){
}
