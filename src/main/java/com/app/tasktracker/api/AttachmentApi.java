package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.AttachmentRequest;
import com.app.tasktracker.dto.response.AttachmentResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.AttachmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Attachment Api", description = "Api methods for attachments")
public class AttachmentApi {

    private final AttachmentService attachmentService;

    @PostMapping
    @Operation(summary = "Save attachment", description = "Save attachment by card id")
    public AttachmentResponse saveAttachment(@RequestBody AttachmentRequest attachmentRequest) {
        return attachmentService.saveAttachmentToCard(attachmentRequest);
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Get attachment ", description = "Get attachment with card id")
    public List<AttachmentResponse> getAttachmentsByCardId(@PathVariable Long cardId) {
        return attachmentService.getAttachmentsByCardId(cardId);
    }

    @DeleteMapping("/{attachmentId}")
    @Operation(summary = "Delete attachment", description = "Delete attachment with id")
    public SimpleResponse deleteAttachment(@PathVariable Long attachmentId) {
        return attachmentService.deleteAttachment(attachmentId);
    }
}