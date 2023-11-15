package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.app.tasktracker.dto.request.AttachmentRequest;
import com.app.tasktracker.dto.response.AttachmentResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.Attachment;
import com.app.tasktracker.models.Card;
import com.app.tasktracker.repositories.AttachmentRepository;
import com.app.tasktracker.repositories.CardRepository;
import com.app.tasktracker.repositories.customRepository.customRepositoryImpl.CustomAttachmentRepositoryImpl;
import com.app.tasktracker.services.AttachmentService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    private final CardRepository cardRepository;
    private final AttachmentRepository attachmentRepository;
    private final CustomAttachmentRepositoryImpl customAttachmentRepository;

    @Override
    public AttachmentResponse saveAttachmentToCard(AttachmentRequest attachmentRequest) {
        log.info("Saving attachment to card with id: {}", attachmentRequest.cardId());
        Card card = cardRepository.findById(attachmentRequest.cardId())
                .orElseThrow(() -> {
                    log.error("Card with id: " + attachmentRequest.cardId() + " not found");
                    return new NotFoundException("Card with id: " + attachmentRequest.cardId() + " not found");
                });
        Attachment attachment = new Attachment();
        attachment.setDocumentLink(attachmentRequest.documentLink());
        attachment.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Bishkek")));
        attachment.setCard(card);
        card.getAttachments().add(attachment);
        attachmentRepository.save(attachment);
        log.info("Attachment saved to card with id: {}", attachmentRequest.cardId());
        return AttachmentResponse.builder()
                .attachmentId(attachment.getId())
                .documentLink(attachment.getDocumentLink())
                .createdAt(attachment.getCreatedAt().toString())
                .build();
    }

    @Override
    public List<AttachmentResponse> getAttachmentsByCardId(Long cardId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> {
                    log.error("Card with id: " + cardId + " not found");
                    return new NotFoundException("Card with id: " + cardId + " not found");
                });
        return customAttachmentRepository.getAttachmentsByCardId(card.getId());
    }

    @Override
    public SimpleResponse deleteAttachment(Long attachmentId) {
        log.info("Deleting attachment with id: {}", attachmentId);
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> {
                    log.error("Attachment with id: " + attachmentId + " not found");
                    return new NotFoundException("Attachment with id: " + attachmentId + " not found");
                });
        Card card = attachment.getCard();
        card.getAttachments().remove(attachment);
        attachmentRepository.deleteById(attachment.getId());
        log.info("Attachment with id: {} deleted successfully", attachmentId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Attachment deleted successfully")
                .build();
    }
}