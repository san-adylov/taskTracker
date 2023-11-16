package com.app.tasktracker.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class LabelResponse {

    private Long labelId;
    private String description;
    private String color;

    public LabelResponse(Long labelId, String description, String color) {
        this.labelId = labelId;
        this.description = description;
        this.color = color;
    }
}