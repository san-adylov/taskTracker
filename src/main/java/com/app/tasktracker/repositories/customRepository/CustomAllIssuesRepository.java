package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.AllIssuesResponse;

import java.time.LocalDate;
import java.util.List;

public interface CustomAllIssuesRepository {

    List<AllIssuesResponse> filterIssues(
            Long workSpaceId,
            LocalDate from,
            LocalDate to,
            List<Long> labelIds,
            List<Long> assigneeIds);

}