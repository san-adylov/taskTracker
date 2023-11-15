package com.app.tasktracker.services;

import jakarta.mail.MessagingException;
import com.app.tasktracker.dto.request.ParticipantsChangeRequest;
import com.app.tasktracker.dto.request.ParticipantsRequest;
import com.app.tasktracker.dto.response.ParticipantsGetAllResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.enums.Role;

public interface ParticipantsService {

    SimpleResponse inviteToWorkSpaces(ParticipantsRequest request) throws MessagingException;

    SimpleResponse participantsRemoveToWorkSpaces(Long workSpaceId,Long userId);

    SimpleResponse changeUpdateRole(ParticipantsChangeRequest request);

    ParticipantsGetAllResponse getParticipantsByRole(Long workSpaceId, Role role);
}
