package com.app.tasktracker.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CardInnerPageResponse {

    private Long cardId;
    private String title;
    private String description;
    private Boolean isArchive;
    private EstimationResponse estimationResponse;
    private List<LabelResponse> labelResponses;
    private List<UserResponse> userResponses;
    private List<CheckListResponse> checklistResponses;
    private List<CommentResponse> commentResponses;

    public CardInnerPageResponse(Long cardId, String title, String description, Boolean isArchive, EstimationResponse estimationResponse, List<LabelResponse> labelResponses, List<UserResponse> userResponses, List<CheckListResponse> checklistResponses, List<CommentResponse> commentResponses) {
        this.cardId = cardId;
        this.title = title;
        this.description = description;
        this.isArchive = isArchive;
        this.estimationResponse = estimationResponse;
        this.labelResponses = labelResponses;
        this.userResponses = userResponses;
        this.checklistResponses = checklistResponses;
        this.commentResponses = commentResponses;
    }

    public CardInnerPageResponse() {
        labelResponses = new ArrayList<>();
        userResponses = new ArrayList<>();
        checklistResponses = new ArrayList<>();
        commentResponses = new ArrayList<>();
    }
}