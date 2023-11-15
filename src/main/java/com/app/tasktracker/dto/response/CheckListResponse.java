package com.app.tasktracker.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckListResponse {

    private Long checkListId;
    private String title;
    private int percent;
    private String counter;
    private List<ItemResponse> itemResponseList;

    public CheckListResponse(Long checkListId, String title, int percent, String counter) {
        this.checkListId = checkListId;
        this.title = title;
        this.percent = percent;
        this.counter = counter;
    }
}