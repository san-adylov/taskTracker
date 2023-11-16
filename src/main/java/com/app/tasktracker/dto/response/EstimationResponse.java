package com.app.tasktracker.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import com.app.tasktracker.enums.ReminderType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimationResponse {

    private Long estimationId;

    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;
    private String startDate;
    private String startTime;
    private String duetDate;
    private String finishTime;

    public EstimationResponse(Long estimationId, ReminderType reminderType, String startDate, String duetDate, String finishTime) {
        this.estimationId = estimationId;
        this.reminderType = reminderType;
        this.startDate = startDate;
        this.duetDate = duetDate;
        this.finishTime = finishTime;
    }
}