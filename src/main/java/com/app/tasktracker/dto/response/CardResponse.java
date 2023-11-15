package com.app.tasktracker.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CardResponse {

    private Long cardId;
    private String title;
    private String duration;
    private String description;
    private int numberOfUsers;
    private int numberOfItems;
    private int numberOfCompletedItems;
    private List<LabelResponse> labelResponses;
    private List<CommentResponse> commentResponses;
    private List<CheckListResponse> checkListResponses;
    private List<AttachmentResponse> attachmentResponses;

    public CardResponse(Long cardId, String title, String duration, String description, int numberOfUsers, int numberOfItems, int numberOfCompletedItems, List<LabelResponse> labelResponses, List<CommentResponse> commentResponses, List<CheckListResponse> checkListResponses, List<AttachmentResponse> attachmentResponses) {
        this.cardId = cardId;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.numberOfUsers = numberOfUsers;
        this.numberOfItems = numberOfItems;
        this.numberOfCompletedItems = numberOfCompletedItems;
        this.labelResponses = labelResponses;
        this.commentResponses = commentResponses;
        this.checkListResponses = checkListResponses;
        this.attachmentResponses = attachmentResponses;
    }
}
