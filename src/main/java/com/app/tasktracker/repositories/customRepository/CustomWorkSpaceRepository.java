package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.WorkSpaceResponse;

import java.util.List;

public interface CustomWorkSpaceRepository {
    List<WorkSpaceResponse> getAllWorkSpaces();

    WorkSpaceResponse getWorkSpaceById(Long workSpaceId);
}
