package com.app.tasktracker.services;

import com.app.tasktracker.dto.request.EstimationRequest;
import com.app.tasktracker.dto.request.UpdateEstimationRequest;
import com.app.tasktracker.dto.response.EstimationResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

public interface EstimationService {

    EstimationResponse createdEstimation(EstimationRequest request);

    SimpleResponse updateEstimation(Long estimationId, UpdateEstimationRequest request);
}
