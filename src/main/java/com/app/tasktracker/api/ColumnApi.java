package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.ColumnRequest;
import com.app.tasktracker.dto.response.ColumnResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.impl.ColumnServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/column")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Column", description = "Api Column to management")
public class ColumnApi {

    private final ColumnServiceImpl columnService;

    @PostMapping
    @Operation(summary = "Create Column", description = "Create column with board id")
    public SimpleResponse createColumn(@RequestBody @Valid ColumnRequest columnRequest) {
        return columnService.createColumn(columnRequest);
    }

    @GetMapping("/{boardId}")
    @Operation(summary = "Get all columns", description = "Get all columns with board id")
    public List<ColumnResponse> getAll(@PathVariable Long boardId) {
        return columnService.getAllColumns(boardId);
    }

    @PutMapping("/{columnId}")
    @Operation(summary = "Update column", description = "Update column by columnId")
    public ColumnResponse updateColumn(@PathVariable Long columnId, @RequestBody @Valid ColumnRequest columnRequest) {
        return columnService.update(columnId, columnRequest);
    }

    @DeleteMapping("/{columnId}")
    @Operation(summary = "Remove column", description = "Remove column by columnId")
    public SimpleResponse removeColumn(@PathVariable @Valid Long columnId) {
        return columnService.removeColumn(columnId);
    }

    @Operation(summary = "Archive a column", description = "archiving and unarchiving column by id")
    @PutMapping("/archive/{columnId}")
    public SimpleResponse archiveCard(@PathVariable Long columnId) {
        return columnService.sendToArchive(columnId);
    }
}