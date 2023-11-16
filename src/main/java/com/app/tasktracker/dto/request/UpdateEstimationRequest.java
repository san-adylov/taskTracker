package com.app.tasktracker.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
public class UpdateEstimationRequest {
    private String reminder;
    private ZonedDateTime startDate;
    private ZonedDateTime dateOfFinish;
    private ZonedDateTime startTime;
    private ZonedDateTime finishTime;

    @Builder
    public UpdateEstimationRequest(String reminder, ZonedDateTime startDate, ZonedDateTime dateOfFinish, ZonedDateTime startTime, ZonedDateTime finishTime) {
        this.reminder = reminder;
        this.startDate = startDate;
        this.dateOfFinish = dateOfFinish;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
}
