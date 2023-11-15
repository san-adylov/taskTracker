package com.app.tasktracker.dto.request;

import lombok.Builder;
import com.app.tasktracker.enums.Role;

@Builder
public record ChangeRoleRequest(
      Long memberId,
      Long boardId,
      Role role
) {
}
