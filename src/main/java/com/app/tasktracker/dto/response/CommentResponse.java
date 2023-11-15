package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String comment;
    private String createdDate;
    private Long creatorId;
    private String creatorName;
    private String creatorAvatar;
    private Boolean isMyComment;

}
