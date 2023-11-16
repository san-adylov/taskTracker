package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.WorkSpaceRequest;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.dto.response.WorkSpaceFavoriteResponse;
import com.app.tasktracker.dto.response.WorkSpaceResponse;
import com.app.tasktracker.services.WorkSpaceService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work_spaces")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN','MEMBER')")
@Tag(name = "Workspace API", description = "All workspace endpoints")
public class WorkSpaceApi {

    private final WorkSpaceService workSpaceService;

    @Operation(summary = "Get all workspaces", description = "Get all workspaces by user auth id")
    @GetMapping
    public List<WorkSpaceResponse> getAllWorkSpaces() {
        return workSpaceService.getAllWorkSpaces();
    }

    @Operation(summary = "Create workSpace", description = "create workspace by user auth id")
    @PostMapping
    public WorkSpaceFavoriteResponse saveWorkSpaces(@RequestBody WorkSpaceRequest workSpaceRequest) throws MessagingException {
        return workSpaceService.saveWorkSpace(workSpaceRequest);
    }

    @Operation(summary = "Get workspace by id ", description = "get workspace by user auth id and own id")
    @GetMapping("/{workSpaceId}")
    public WorkSpaceResponse getWorkSpaceById(@PathVariable Long workSpaceId) {
        return workSpaceService.getWorkSpaceById(workSpaceId);
    }

    @Operation(summary = "Update workspace by id", description = "get workspace by user auth id and own id")
    @PutMapping("/{workSpaceId}")
    public SimpleResponse updateWorkSpaceById(@PathVariable Long workSpaceId, @RequestBody WorkSpaceRequest workSpaceRequest) {
        return workSpaceService.updateWorkSpaceById(workSpaceId, workSpaceRequest);
    }

    @Operation(summary = "delete workspace by id", description = "get workspace by user auth id and own id")
    @DeleteMapping("/{workSpaceId}")
    public SimpleResponse deleteWorkSpaceById(@PathVariable Long workSpaceId) {
        return workSpaceService.deleteWorkSpaceById(workSpaceId);
    }
}
