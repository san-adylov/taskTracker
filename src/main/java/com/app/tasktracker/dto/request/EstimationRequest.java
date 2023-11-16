package com.app.tasktracker.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EstimationRequest {
    private Long cardId;
    private String reminder;
    private ZonedDateTime startDate;
    private ZonedDateTime dateOfFinish;
    private ZonedDateTime startTime;
    private ZonedDateTime finishTime;

    @Builder
    public EstimationRequest(Long cardId, String reminder, ZonedDateTime startDate, ZonedDateTime dateOfFinish, ZonedDateTime startTime, ZonedDateTime finishTime) {
        this.cardId = cardId;
        this.reminder = reminder;
        this.startDate = startDate;
        this.dateOfFinish = dateOfFinish;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
}