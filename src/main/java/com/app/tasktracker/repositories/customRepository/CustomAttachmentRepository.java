package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.AttachmentResponse;

import java.util.List;

public interface CustomAttachmentRepository {

    List<AttachmentResponse> getAttachmentsByCardId(Long cardId);
}
