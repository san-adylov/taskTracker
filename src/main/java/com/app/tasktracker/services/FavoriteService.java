package com.app.tasktracker.services;

import com.app.tasktracker.dto.response.BoardResponse;
import com.app.tasktracker.dto.response.FavoriteResponse;
import com.app.tasktracker.dto.response.WorkSpaceFavoriteResponse;

public interface FavoriteService {

    BoardResponse saveBoardFavorite(Long boardId);

    WorkSpaceFavoriteResponse saveWorkSpaceFavorite(Long workSpaceId);

    FavoriteResponse getAllFavorites();
}
