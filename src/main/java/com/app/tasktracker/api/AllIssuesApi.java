package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.response.AllIssuesResponse;
import com.app.tasktracker.services.AllIssuesService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/all-issues")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "All issues Api", description = "All issues endpoints")
public class AllIssuesApi {

    private final AllIssuesService allIssuesService;

    @GetMapping("/filter")
    @Operation(summary = "Filter all Issues", description = "Retrieve a filtered list of issues (cards) within a workspace, using criteria such as date range, labels, and assignees.")
    public List<AllIssuesResponse> filterCards(
            @RequestParam(value = "workSpaceId") Long workSpaceId,
            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(value = "labels", required = false) List<Long> labelIds,
            @RequestParam(value = "assignees", required = false) List<Long> assigneeMemberIds) {
        return allIssuesService.filterIssues(workSpaceId, from, to, labelIds, assigneeMemberIds);
    }
}