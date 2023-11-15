package com.app.tasktracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllIssuesResponse {

    private Long cardId;
    private LocalDate created;
    private String durationDay;
    private Long creatorId;
    private String creatorFullName;
    private String column;
    private List<UserAllIssuesResponse> assignee;
    private List<LabelResponse> labelResponses;
    private String checkListResponse;
    private String description;
    private Boolean isChecked;

    public AllIssuesResponse(Long cardId, LocalDate created, String durationDay, Long creatorId, String creatorFullName, String column, String checkListResponse, String description) {
        this.cardId = cardId;
        this.created = created;
        this.durationDay = durationDay;
        this.creatorId = creatorId;
        this.creatorFullName = creatorFullName;
        this.column = column;
        this.checkListResponse = checkListResponse;
        this.description = description;
    }
}