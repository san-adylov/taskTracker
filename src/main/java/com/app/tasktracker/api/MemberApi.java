package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.tasktracker.dto.request.ChangeRoleRequest;
import com.app.tasktracker.dto.request.InviteRequest;
import com.app.tasktracker.dto.response.MemberResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.services.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Member Api", description = "Api methods for members")
public class MemberApi {

    private final MemberService memberService;

    @GetMapping("/search")
    @Operation(summary = "Search ", description = "User search members by email or name")
    public List<MemberResponse> searchUserByEmailOrName(@RequestParam(name = "workSpaceId") Long workSpaceId, @RequestParam(name = "email") String email) {
        return memberService.searchByEmail(workSpaceId, email);
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "All members", description = "Get members by card id")
    public List<MemberResponse> getAllMembers(@PathVariable Long cardId) {
        return memberService.getAllMembersByCardId(cardId);
    }

    @Operation(summary = "Invite new member to board", description = "Invite new member to board")
    @PostMapping("/board-invite")
    public SimpleResponse inviteParticipant(@RequestBody InviteRequest inviteRequest) throws MessagingException {
        return memberService.inviteMemberToBoard(inviteRequest);
    }

    @Operation(summary = "Change member role", description = "Change member role")
    @PutMapping("/change-role")
    public SimpleResponse changeMemberRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
        return memberService.changeMemberRole(changeRoleRequest);
    }

    @GetMapping("/boards-members/{boardId}")
    @Operation(summary = "All invited members in board", description = "Get all invited members by board id ")
    public List<MemberResponse> getAllMembersFromBoard(@PathVariable Long boardId) {
        return memberService.getAllMembersFromBoard(boardId);
    }

    @Operation(summary = "Assign user to card", description = "Assign user to card")
    @PostMapping("/{memberId}/{cardId}")
    public SimpleResponse assignToCard(@PathVariable Long memberId, @PathVariable Long cardId) {
        return memberService.assignMemberToCard(memberId, cardId);
    }

    @Operation(summary = "Remove member", description = "Remove members by id from board ")
    @DeleteMapping("/{memberId}/{boardId}")
    public SimpleResponse removeMemberFromBoard(@PathVariable Long memberId, @PathVariable Long boardId) {
        return memberService.removeMemberFromBoard(memberId, boardId);
    }
}