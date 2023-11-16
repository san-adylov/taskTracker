package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.ItemRequest;
import com.app.tasktracker.dto.response.ItemResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN','MEMBER')")
@Tag(name = "Item API", description = "API for managing items")
public class ItemApi {

    private final ItemService itemService;

    @PostMapping
    @Operation(summary = "Save item", description = "save item by checkList id")
    public ItemResponse saveItem(@RequestBody ItemRequest itemRequest) {
        return itemService.saveItem(itemRequest);
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Add to completed", description = "Add to completed item and remove from completed by item id")
    public ItemResponse addToCompletedAndRemoveFromCompleted(@PathVariable Long itemId) {
        return itemService.addToCompletedAndRemoveFromCompleted(itemId);
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Delete item", description = "delete item by item id")
    public SimpleResponse deleteItem(@PathVariable Long itemId) {
        return itemService.deleteItem(itemId);
    }
}
