package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.ColumnRequest;
import com.app.tasktracker.dto.response.ColumnResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface ColumnService {

    SimpleResponse createColumn(ColumnRequest columnRequest);

    List<ColumnResponse> getAllColumns(Long boardId);

    ColumnResponse update(Long columnId,ColumnRequest columnRequest);

    SimpleResponse removeColumn(Long columnId);

    SimpleResponse sendToArchive(Long columnId);
}