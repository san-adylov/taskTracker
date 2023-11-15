package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.CardService;
import com.app.tasktracker.dto.request.CardRequest;
import com.app.tasktracker.dto.request.UpdateCardRequest;
import com.app.tasktracker.dto.response.CardInnerPageResponse;
import com.app.tasktracker.dto.response.CardResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Card API", description = "API for managing cards")
public class CardApi {

    private final CardService cardService;

    @Operation(summary = "Archive a card", description = "archiving and unarchiving card by id")
    @PutMapping("/archive/{cardId}")
    public SimpleResponse archiveCard(@PathVariable Long cardId) {
        return cardService.archiveCard(cardId);
    }

    @Operation(summary = "Archive all cards", description = "Archives all cards in a specific column by id")
    @PutMapping("/all-archive/{columnId}")
    public SimpleResponse archiveAllCardsInColumn(@PathVariable Long columnId) {
        return cardService.archiveAllCardsInColumn(columnId);
    }

    @Operation(summary = "Delete all cards", description = "This operation allows deleting all cards in a column by its ID")
    @DeleteMapping("/all/{columnId}")
    public SimpleResponse deleteAllCardsInColumn(@PathVariable Long columnId) {
        return cardService.deleteAllCardsInColumn(columnId);
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Get card", description = "get inner page card by card id")
    public CardInnerPageResponse getInnerPageCardById(@PathVariable Long cardId) {
        return cardService.getInnerPageCardById(cardId);
    }

    @GetMapping("/column-cards/{columnId}")
    @Operation(summary = "Get all cards", description = "get all cards by column id")
    public List<CardResponse> getCardsByColumnId(@PathVariable Long columnId) {
        return cardService.getAllCardsByColumnId(columnId);
    }

    @PostMapping
    @Operation(summary = "Save card", description = "save card by column id")
    public CardInnerPageResponse saveCard(@RequestBody CardRequest cardRequest) {
        return cardService.saveCard(cardRequest);
    }

    @PutMapping
    @Operation(summary = "Update card", description = "update card with card id")
    public SimpleResponse updateCard(@RequestBody UpdateCardRequest updateCardRequest) {
        return cardService.updateCardById(updateCardRequest);
    }

    @DeleteMapping("/{cardId}")
    @Operation(summary = "Delete card", description = "delete card with card id")
    public SimpleResponse deleteCard(@PathVariable Long cardId) {
        return cardService.deleteCard(cardId);
    }

    @PutMapping("/move-card/{cardId}/{columnId}")
    @Operation(summary = "Move card", description = "Move card to column by card id and column id")
    public SimpleResponse moveCard(@PathVariable Long cardId, @PathVariable Long columnId) {
        return cardService.moveCard(cardId, columnId);
    }
}
