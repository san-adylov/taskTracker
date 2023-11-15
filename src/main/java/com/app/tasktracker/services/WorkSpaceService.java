package com.app.tasktracker.services;


import jakarta.mail.MessagingException;
import com.app.tasktracker.dto.request.WorkSpaceRequest;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.dto.response.WorkSpaceFavoriteResponse;
import com.app.tasktracker.dto.response.WorkSpaceResponse;

import java.util.List;

public interface WorkSpaceService {

    List<WorkSpaceResponse> getAllWorkSpaces();

    WorkSpaceFavoriteResponse saveWorkSpace(WorkSpaceRequest workSpaceRequest) throws MessagingException;

    WorkSpaceResponse getWorkSpaceById(Long id);

    SimpleResponse updateWorkSpaceById(Long id, WorkSpaceRequest workSpaceRequest);

    SimpleResponse deleteWorkSpaceById(Long id);

}