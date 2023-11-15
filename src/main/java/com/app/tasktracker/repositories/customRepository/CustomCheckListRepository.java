package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.CheckListResponse;

import java.util.List;

public interface CustomCheckListRepository {

    List<CheckListResponse> getAllCheckListByCardId(Long cardId);

}