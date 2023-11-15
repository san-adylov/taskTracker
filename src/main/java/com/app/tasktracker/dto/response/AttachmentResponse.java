package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponse {

    private Long attachmentId;
    private String documentLink;
    private String createdAt;
}