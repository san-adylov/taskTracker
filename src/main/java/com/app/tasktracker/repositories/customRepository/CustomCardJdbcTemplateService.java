package com.app.tasktracker.repositories.jdbcTemplateService;

import com.app.tasktracker.dto.response.CardInnerPageResponse;
import com.app.tasktracker.dto.response.CardResponse;

import java.util.List;

public interface CustomCardJdbcTemplateService {

    CardInnerPageResponse getAllCardInnerPage(Long cardId);

    List<CardResponse> getAllCardsByColumnId(Long columnId);

}