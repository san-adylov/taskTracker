package com.app.tasktracker.services;

import jakarta.mail.MessagingException;
import com.app.tasktracker.dto.request.ChangeRoleRequest;
import com.app.tasktracker.dto.request.InviteRequest;
import com.app.tasktracker.dto.response.MemberResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface MemberService {

    List<MemberResponse> searchByEmail(Long workSpaceId, String email);

    List<MemberResponse> getAllMembersByCardId(Long cardId);

    SimpleResponse inviteMemberToBoard(InviteRequest request) throws MessagingException;

    SimpleResponse changeMemberRole(ChangeRoleRequest request);

    List<MemberResponse> getAllMembersFromBoard(Long boardId);

    SimpleResponse assignMemberToCard(Long memberId,Long cardId);

    SimpleResponse removeMemberFromBoard(Long memberId,Long boardId);

}