package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.CheckListRequest;
import com.app.tasktracker.dto.response.CheckListResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.CheckListService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checkList")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "CheckList API", description = "API for managing checkList")
public class CheckListApi {

    private final CheckListService checkListService;

    @GetMapping("/{cardId}")
    @Operation(summary = "Get all checkList", description = "get all checkList by card id")
    public List<CheckListResponse> getCheckListsByCardId(@PathVariable Long cardId) {
        return checkListService.getAllCheckListByCardId(cardId);
    }

    @PostMapping("/{cardId}")
    @Operation(summary = "Save checkList", description = "save checkList by card id")
    public CheckListResponse saveCheckList(@PathVariable Long cardId, @RequestBody CheckListRequest request) {
        return checkListService.saveCheckList(cardId, request);
    }

    @PutMapping("/{checkListId}")
    @Operation(summary = "Update checkList", description = "update checkList by checkList id")
    public CheckListResponse updateCheckList(@PathVariable Long checkListId, @RequestBody CheckListRequest request) {
        return checkListService.updateCheckListById(checkListId, request);
    }

    @DeleteMapping("/{checkListId}")
    @Operation(summary = "Delete checkList", description = "delete checkList by checkList id")
    public SimpleResponse deleteCheckList(@PathVariable Long checkListId) {
        return checkListService.deleteCheckList(checkListId);
    }
}