package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.BoardRequest;
import com.app.tasktracker.dto.request.BoardUpdateRequest;
import com.app.tasktracker.dto.response.BoardResponse;
import com.app.tasktracker.dto.response.FilterBoardResponse;
import com.app.tasktracker.dto.response.GetAllArchiveResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN','MEMBER')")
@Tag(name = "Board Api", description = "Api methods for boards")
public class BoardApi {

    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "Save board", description = "Save board by workspace id")
    public BoardResponse saveBoard(@RequestBody BoardRequest boardRequest) {
        return boardService.saveBoard(boardRequest);
    }

    @GetMapping("/get-all/{workSpaceId}")
    @Operation(summary = "Get all boards", description = "Get all boards by workspace id")
    public List<BoardResponse> getAllBoarsByWorkSpace(@PathVariable Long workSpaceId) {
        return boardService.getAllBoardsByWorkspaceId(workSpaceId);
    }

    @GetMapping("/{boardId}")
    @Operation(summary = "Get board", description = "Get board with id")
    public BoardResponse getById(@PathVariable Long boardId) {
        return boardService.getBoardById(boardId);
    }

    @PutMapping
    @Operation(summary = "Update board", description = "Update board with id")
    public SimpleResponse update(@RequestBody @Valid BoardUpdateRequest boardUpdateRequest) {
        return boardService.updateBoard(boardUpdateRequest);
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "Delete board", description = "Delete board with id")
    public SimpleResponse deleteBoard(@PathVariable Long boardId) {
        return boardService.deleteBoard(boardId);
    }

    @GetMapping("/get-all-archive/{boardId}")
    @Operation(summary = "Get all archives", description = "Get all archived cards and columns by board id")
    public GetAllArchiveResponse getAllArchives(@PathVariable Long boardId) {
        return boardService.getAllArchivedCardsAndColumns(boardId);
    }

    @GetMapping("/{boardId}/filter")
    public FilterBoardResponse filterByConditions(
            @PathVariable Long boardId,
            @RequestParam(required = false) boolean noDates,
            @RequestParam(required = false) boolean overdue,
            @RequestParam(required = false) boolean dueNextDay,
            @RequestParam(required = false) boolean dueNextWeek,
            @RequestParam(required = false) boolean dueNextMonth,
            @RequestParam(required = false) List<Long> labelIds) {
        return boardService.filterByConditions(boardId, noDates, overdue, dueNextDay, dueNextWeek, dueNextMonth, labelIds);
    }
}