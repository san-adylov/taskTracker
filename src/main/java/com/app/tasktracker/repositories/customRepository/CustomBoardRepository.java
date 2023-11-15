package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.BoardResponse;
import com.app.tasktracker.dto.response.FilterBoardResponse;
import com.app.tasktracker.dto.response.GetAllArchiveResponse;

import java.util.List;

public interface CustomBoardRepository {

    List<BoardResponse> getAllBoardsByWorkspaceId(Long workSpaceId);
    GetAllArchiveResponse getAllArchivedCardsAndColumns(Long boardId);
    FilterBoardResponse filterByConditions(Long boardId, boolean noDates, boolean overdue, boolean dueNextDay, boolean dueNextWeek, boolean dueNextMonth, List<Long>labelIds);

}
