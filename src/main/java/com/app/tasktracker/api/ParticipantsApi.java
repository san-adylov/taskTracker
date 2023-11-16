package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.ParticipantsChangeRequest;
import com.app.tasktracker.dto.request.ParticipantsRequest;
import com.app.tasktracker.dto.response.ParticipantsGetAllResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.enums.Role;
import com.app.tasktracker.services.ParticipantsService;


@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/participants")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Participants api", description = "Api participants to management")
public class ParticipantsApi {

    private final ParticipantsService participantsService;

    @Operation(summary = "Invite participants", description = "Invite participants to workSpace by workSpace id")
    @PostMapping
    public SimpleResponse inviteToWorkSpace(@RequestBody ParticipantsRequest participantsRequest) throws MessagingException {
        return participantsService.inviteToWorkSpaces(participantsRequest);
    }

    @Operation(summary = "Change role", description = "Participants change role update to workspace by workSpace id")
    @PutMapping
    public SimpleResponse changeUpdateRole(@RequestBody ParticipantsChangeRequest request) {
        return participantsService.changeUpdateRole(request);
    }

    @Operation(summary = "Get all participants", description = "You can get all participants with workSpace id and sort by their roles")
    @GetMapping("/{wokSpaceId}")
    public ParticipantsGetAllResponse getAllParticipants(@PathVariable Long wokSpaceId, @RequestParam Role role) {
        return participantsService.getParticipantsByRole(wokSpaceId, role);
    }

    @Operation(summary = "Remove participants", description = "Remove participants to workSpace id")
    @DeleteMapping("/{workSpaceId}/{userId}")
    public SimpleResponse removeParticipants(@PathVariable Long workSpaceId, @PathVariable Long userId) {
        return participantsService.participantsRemoveToWorkSpaces(workSpaceId, userId);
    }
}
