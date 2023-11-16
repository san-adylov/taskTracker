package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.LabelRequest;
import com.app.tasktracker.dto.response.LabelResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.LabelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labels")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Labels API", description = "All labels endpoints")
public class LabelApi {

    private final LabelService labelService;

    @Operation(summary = "Get all labels", description = "Get all labels ")
    @GetMapping()
    public List<LabelResponse> getAllLabels() {
        return labelService.getAllLabels();
    }

    @Operation(summary = "Save label", description = "save label  ")
    @PostMapping
    public SimpleResponse saveLabels(@RequestBody LabelRequest labelRequest) {
        return labelService.saveLabels(labelRequest);
    }

    @Operation(summary = "Add label to card", description = "Find label and card and add label to card ")
    @PutMapping("/add-label-to-card/{cardId}/{labelId}")
    public SimpleResponse addLabelsToCard(@PathVariable Long cardId, @PathVariable Long labelId) {
        return labelService.addLabelsToCard(cardId, labelId);
    }

    @Operation(summary = "Get by id", description = "Get label by id ")
    @GetMapping("/{labelId}")
    public LabelResponse getLabelById(@PathVariable Long labelId) {
        return labelService.getLabelById(labelId);
    }

    @Operation(summary = "Update labels", description = "Update labels by id ")
    @PutMapping("/{labelId}")
    public SimpleResponse updateLabelDeleteById(@PathVariable Long labelId, @RequestBody LabelRequest labelRequest) {
        return labelService.updateLabelDeleteById(labelId, labelRequest);
    }

    @Operation(summary = "Delete label by id", description = "Delete label by id!    ")
    @DeleteMapping("/{labelId}/{cardId}")
    public SimpleResponse deleteLabelById(@PathVariable Long cardId, @PathVariable Long labelId) {
        return labelService.deleteFromCardByIdLables(cardId, labelId);
    }

    @Operation(summary = "Get all label by card id", description = " Get all labels by card id")
    @GetMapping("/card-labels/{cardId}")
    public List<LabelResponse> getAllLabelsByCardId(@PathVariable Long cardId) {
        return labelService.getAllLabelsByCardId(cardId);
    }
}
