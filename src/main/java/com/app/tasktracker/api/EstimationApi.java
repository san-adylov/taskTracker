package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.EstimationRequest;
import com.app.tasktracker.dto.request.UpdateEstimationRequest;
import com.app.tasktracker.dto.response.EstimationResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.EstimationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estimations")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Estimation", description = "Api Estimation to management")
public class EstimationApi {

    private final EstimationService service;

    @PostMapping
    @Operation(summary = "Create estimation", description = "Create estimation is the card id")
    public EstimationResponse createdEstimation(@RequestBody EstimationRequest estimationRequest) {
        return service.createdEstimation(estimationRequest);
    }

    @PutMapping("/{estimationId}")
    @Operation(summary = "Update estimation", description = "Update estimation is the card id")
    public SimpleResponse updatedEstimation(@PathVariable Long estimationId, @RequestBody UpdateEstimationRequest estimationRequest) {
        return service.updateEstimation(estimationId, estimationRequest);
    }
}
