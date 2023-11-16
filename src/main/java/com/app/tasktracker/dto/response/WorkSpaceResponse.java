package com.app.tasktracker.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WorkSpaceResponse {
    private Long workSpaceId;
    private String workSpaceName;
    private Long adminId;
    private String adminFullName;
    private String adminImage;
    private Boolean isFavorite;

    public WorkSpaceResponse(Long workSpaceId, String workSpaceName) {
        this.workSpaceId = workSpaceId;
        this.workSpaceName = workSpaceName;
    }
}