package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.ItemRequest;
import com.app.tasktracker.dto.response.ItemResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

public interface ItemService {

     ItemResponse saveItem(ItemRequest itemRequest);

     ItemResponse addToCompletedAndRemoveFromCompleted(Long itemId);

     SimpleResponse deleteItem(Long itemId);

}