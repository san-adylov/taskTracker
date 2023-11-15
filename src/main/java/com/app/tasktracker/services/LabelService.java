package com.app.tasktracker.services;


import com.app.tasktracker.dto.request.LabelRequest;
import com.app.tasktracker.dto.response.LabelResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface LabelService {

    List<LabelResponse> getAllLabels();
    SimpleResponse saveLabels(LabelRequest labelRequest);
    SimpleResponse addLabelsToCard(Long cardId,Long labelId);

    LabelResponse getLabelById(Long labelId);

    SimpleResponse updateLabelDeleteById(Long labelId, LabelRequest labelRequest);

    SimpleResponse deleteFromCardByIdLables(Long cardId,Long labelId);

    List<LabelResponse> getAllLabelsByCardId(Long cardId);

}
