package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.response.BoardResponse;
import com.app.tasktracker.dto.response.FavoriteResponse;
import com.app.tasktracker.dto.response.WorkSpaceFavoriteResponse;
import com.app.tasktracker.services.FavoriteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Favourite Api", description = "Api methods for favorites")
public class FavoriteApi {

    private final FavoriteService favoriteService;

    @Operation(summary = "Save favorite", description = "Make and delete board favorites")
    @PostMapping("/board/{boardId}")
    public BoardResponse saveBoardFavorite(@PathVariable Long boardId) {
        return favoriteService.saveBoardFavorite(boardId);
    }

    @Operation(summary = "Save favorite", description = "Make and delete work_space favorites")
    @PostMapping("/work_space/{workSpaceId}")
    public WorkSpaceFavoriteResponse saveWorkSpaceFavorite(@PathVariable Long workSpaceId) {
        return favoriteService.saveWorkSpaceFavorite(workSpaceId);
    }

    @Operation(summary = "All favorites", description = "All favorites in board and workSpace")
    @GetMapping
    public FavoriteResponse getAllFavorites() {
        return favoriteService.getAllFavorites();
    }
}