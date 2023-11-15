package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.app.tasktracker.dto.request.CheckListRequest;
import com.app.tasktracker.dto.response.CheckListResponse;
import com.app.tasktracker.dto.response.ItemResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.*;
import com.app.tasktracker.repositories.*;
import com.app.tasktracker.repositories.customRepository.CustomCheckListRepository;
import com.app.tasktracker.services.CheckListService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CheckListRepository checkListRepository;
    private final CustomCheckListRepository customCheckListRepository;
    private final ItemRepository itemRepository;

    public User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User not found!"));
    }

    @Override
    public CheckListResponse saveCheckList(Long cardId, CheckListRequest checkListRequest) {

        Card card = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error("Card with id: " + cardId + " not found!");
            return new NotFoundException("Card with id: " + cardId + " not found!");
        });

        CheckList checkList = new CheckList();
        checkList.setTitle(checkListRequest.title());
        checkList.setCard(card);
        checkListRepository.save(checkList);

        log.info("CheckList successfully saved!");
        return CheckListResponse.builder()
                .checkListId(checkList.getId())
                .title(checkList.getTitle())
                .percent(checkList.getPercent())
                .counter("0/0")
                .itemResponseList(new ArrayList<>())
                .build();
    }

    @Override
    public List<CheckListResponse> getAllCheckListByCardId(Long cardId) {
        cardRepository.findById(cardId).orElseThrow(() -> {
            log.error("Card with id: " + cardId + " not found!");
            return new NotFoundException("Card with id: " + cardId + " not found!");
        });
        return customCheckListRepository.getAllCheckListByCardId(cardId);
    }

    @Override
    public CheckListResponse updateCheckListById(Long checkListId, CheckListRequest checkListRequest) {

        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> {
            log.error("CheckList with id: " + checkListId + " not found!");
            return new NotFoundException("CheckList with id: " + checkListId + " not found!");
        });

        checkList.setTitle(checkListRequest.title());
        log.info("CheckList successfully updated!");
        return convertCheckListToResponse(checkListRepository.save(checkList));
    }

    @Override
    public SimpleResponse deleteCheckList(Long checkListId) {

        CheckList checkList = checkListRepository.findById(checkListId).orElseThrow(() -> {
            log.error("CheckList with id: " + checkListId + " not found!");
            return new NotFoundException("CheckList with id: " + checkListId + " not found!");
        });

        List<Item> items = new ArrayList<>(checkList.getItems());
        for (Item item : items) {
            item.setCheckList(null);
            itemRepository.delete(item);
        }
        checkListRepository.delete(checkList);

        log.info("CheckList with id: " + checkListId + " successfully deleted!");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("CheckList with id: " + checkListId + " successfully deleted!")
                .build();
    }

    public CheckListResponse convertCheckListToResponse(CheckList checklist) {
        CheckListResponse checkListResponse = new CheckListResponse();
        checkListResponse.setCheckListId(checklist.getId());
        checkListResponse.setTitle(checklist.getTitle());

        List<Item> items = checklist.getItems();

        if (items != null) {
            List<ItemResponse> itemResponses = new ArrayList<>();
            int countOfItems = items.size();
            int countOfCompletedItems = 0;

            for (Item item : items) {
                ItemResponse itemResponse = new ItemResponse();
                itemResponse.setItemId(item.getId());
                itemResponse.setTitle(item.getTitle());
                itemResponse.setIsDone(item.getIsDone());
                itemResponses.add(itemResponse);

                if (item.getIsDone().equals(true)) {
                    countOfCompletedItems++;
                }
            }
            int count = (countOfItems > 0) ? (countOfCompletedItems * 100) / countOfItems : 0;
            String counter = countOfCompletedItems + "/" + countOfItems;

            checkListResponse.setPercent(count);
            checkListResponse.setCounter(counter);
            checkListResponse.setItemResponseList(itemResponses);

            checklist.setPercent(count);
            checkListRepository.save(checklist);
        } else {
            checkListResponse.setCounter("0/0");
            checkListResponse.setItemResponseList(new ArrayList<>());
        }
        return checkListResponse;
    }
}
