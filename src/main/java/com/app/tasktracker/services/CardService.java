package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.CardRequest;
import com.app.tasktracker.dto.request.UpdateCardRequest;
import com.app.tasktracker.dto.response.CardInnerPageResponse;
import com.app.tasktracker.dto.response.CardResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface CardService {

    CardInnerPageResponse saveCard(CardRequest cardRequest);

    CardInnerPageResponse getInnerPageCardById(Long cardId);

    List<CardResponse> getAllCardsByColumnId(Long columnId);

    SimpleResponse updateCardById(UpdateCardRequest updateCardRequest);

    SimpleResponse deleteCard(Long id);

    SimpleResponse archiveCard(Long cardId);

    SimpleResponse deleteAllCardsInColumn(Long columnId);

    SimpleResponse archiveAllCardsInColumn(Long columnId);

    SimpleResponse moveCard(Long cardId, Long columnId);

}