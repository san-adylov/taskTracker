package com.app.tasktracker.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllArchiveResponse {

    private List<CardResponse> cardResponses;
    private List<ColumnResponse> columnResponses;
}