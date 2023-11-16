package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.app.tasktracker.dto.request.ItemRequest;
import com.app.tasktracker.dto.response.ItemResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.CheckList;
import com.app.tasktracker.models.Item;
import com.app.tasktracker.repositories.CheckListRepository;
import com.app.tasktracker.repositories.ItemRepository;
import com.app.tasktracker.services.ItemService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CheckListRepository checkListRepository;

    @Override
    public ItemResponse saveItem(ItemRequest itemRequest) {

        CheckList checkList = checkListRepository.findById(itemRequest.checkListId()).orElseThrow(() -> {
            log.error("CheckList with id: " + itemRequest.checkListId() + " not found!");
            return new NotFoundException("CheckList with id: " + itemRequest.checkListId() + " not found!");
        });
        Item item = new Item();
        item.setTitle(itemRequest.title());
        item.setIsDone(false);
        item.setCheckList(checkList);
        checkList.getItems().add(item);
        itemRepository.save(item);

        log.info("Item successfully saved!");
        return ItemResponse.builder()
                .itemId(item.getId())
                .title(item.getTitle())
                .isDone(item.getIsDone())
                .build();
    }

    @Override
    public ItemResponse addToCompletedAndRemoveFromCompleted(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            log.error("Item with id: " + itemId + " not found!");
            return new NotFoundException("Item with id: " + itemId + " not found!");
        });
        item.setIsDone(!item.getIsDone());
        itemRepository.save(item);

        return ItemResponse.builder()
                .itemId(item.getId())
                .title(item.getTitle())
                .isDone(item.getIsDone())
                .build();
    }

    @Override
    public SimpleResponse deleteItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            log.error("Item with id: " + itemId + " not found!");
            return new NotFoundException("Item with id: " + itemId + " not found!");
        });
        itemRepository.delete(item);

        log.info("Item with id: " + itemId + " successfully deleted!");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Item with id: " + itemId + " successfully deleted!")
                .build();
    }
}
