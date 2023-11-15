package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.CheckListRequest;
import com.app.tasktracker.dto.response.CheckListResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface CheckListService {

    CheckListResponse saveCheckList(Long cardId, CheckListRequest checkListRequest);

    List<CheckListResponse> getAllCheckListByCardId(Long cardId);

    CheckListResponse updateCheckListById(Long checkListId,CheckListRequest checkListRequest);

    SimpleResponse deleteCheckList(Long checkListId);

}