package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.ColumnResponse;

import java.util.List;

public interface CustomColumnRepository {

    List<ColumnResponse> getAllColumns(Long boardId);
}
