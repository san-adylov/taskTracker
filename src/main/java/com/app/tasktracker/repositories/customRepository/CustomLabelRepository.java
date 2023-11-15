package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.LabelResponse;

import java.util.List;

public interface CustomLabelRepository {
    List<LabelResponse> getAllLabels();
    List<LabelResponse> getAllLabelsByCardId(Long cardId);
    LabelResponse getLabelById(Long labelId);
}
