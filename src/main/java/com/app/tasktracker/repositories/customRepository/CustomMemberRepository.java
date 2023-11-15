package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.MemberResponse;

import java.util.List;

public interface CustomMemberRepository {

    List<MemberResponse> getAllMembersByCardId(Long cardId);
    List<MemberResponse> searchByEmail(Long workSpaceId, String email);
    List<MemberResponse> getAllMembersFromBoard(Long boardId);
}
