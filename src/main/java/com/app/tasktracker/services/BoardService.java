package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.BoardRequest;
import com.app.tasktracker.dto.request.BoardUpdateRequest;
import com.app.tasktracker.dto.response.BoardResponse;
import com.app.tasktracker.dto.response.FilterBoardResponse;
import com.app.tasktracker.dto.response.GetAllArchiveResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface BoardService {

    List<BoardResponse> getAllBoardsByWorkspaceId(Long workSpaceId);

    BoardResponse saveBoard(BoardRequest boardRequest);

    SimpleResponse updateBoard(BoardUpdateRequest boardUpdateRequest);

    SimpleResponse deleteBoard(Long boardId);

    BoardResponse getBoardById(Long boardId);

    GetAllArchiveResponse getAllArchivedCardsAndColumns(Long boardId);

    FilterBoardResponse filterByConditions(Long boardId, boolean noDates, boolean overdue, boolean dueNextDay, boolean dueNextWeek, boolean dueNextMonth,List<Long>labelIds);

}