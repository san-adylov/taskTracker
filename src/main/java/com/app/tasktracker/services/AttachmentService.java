package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.AttachmentRequest;
import com.app.tasktracker.dto.response.AttachmentResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface AttachmentService {

    AttachmentResponse saveAttachmentToCard(AttachmentRequest attachmentRequest);

    List<AttachmentResponse> getAttachmentsByCardId(Long cardId);

    SimpleResponse deleteAttachment(Long attachmentId);
}
