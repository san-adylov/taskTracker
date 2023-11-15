package com.app.tasktracker.services;

import com.app.tasktracker.dto.response.AllIssuesResponse;

import java.time.LocalDate;
import java.util.List;

public interface AllIssuesService {

    List<AllIssuesResponse> filterIssues(
            Long workSpaceId,
            LocalDate from,
            LocalDate to,
            List<Long> labelIds,
            List<Long> assigneeMemberIds);

}