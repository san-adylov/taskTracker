package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.ParticipantsGetAllResponse;
import com.app.tasktracker.enums.Role;

public interface CustomParticipantsRepository {

    ParticipantsGetAllResponse getParticipantsByRole(Long workSpaceId, Role role);
}
