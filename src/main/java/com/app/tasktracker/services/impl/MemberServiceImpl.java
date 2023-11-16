package com.app.tasktracker.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.request.ChangeRoleRequest;
import com.app.tasktracker.dto.request.InviteRequest;
import com.app.tasktracker.dto.response.MemberResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.enums.NotificationType;
import com.app.tasktracker.exceptions.BadCredentialException;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.*;
import com.app.tasktracker.repositories.*;
import com.app.tasktracker.repositories.customRepository.customRepositoryImpl.CustomMemberRepositoryImpl;
import com.app.tasktracker.services.MemberService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final UserWorkSpaceRoleRepository userWorkSpaceRoleRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final WorkSpaceRepository workSpaceRepository;
    private final CardRepository cardRepository;
    private final CustomMemberRepositoryImpl customMemberRepository;
    private final JavaMailSender javaMailSender;
    private final NotificationRepository notificationRepository;

    @Override
    public List<MemberResponse> searchByEmail(Long workSpaceId, String email) {

        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> {
                    log.error("WorkSpace with id: " + workSpaceId + " not found");
                    throw new NotFoundException("WorkSpace with id: " + workSpaceId + " not found");
                });
        return customMemberRepository.searchByEmail(workSpace.getId(), email);
    }

    @Override
    public List<MemberResponse> getAllMembersByCardId(Long cardId) {

        Card card = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error("Card with id: " + cardId + " not found");
            throw new NotFoundException("Card with id: " + cardId + " not found");
        });
        return customMemberRepository.getAllMembersByCardId(card.getId());
    }

    public SimpleResponse inviteMemberToBoard(InviteRequest request) throws MessagingException {
        log.info("Inviting member with email: {} to board with id: {}", request.getEmail(), request.getBoardId());
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> {
                    log.error("Board with id: " + request.getBoardId() + " not found");
                    throw new NotFoundException("Board with id: " + request.getBoardId() + " not found");
                });
        WorkSpace workSpace = workSpaceRepository.findById(board.getWorkSpace().getId())
                .orElseThrow(() -> {
                    log.error("WorkSpace with id: " + board.getWorkSpace().getId() + " not found");
                    throw new NotFoundException("WorkSpace with id: " + board.getWorkSpace().getId() + " not found");
                });
        if (userRepository.existsByEmail(request.getEmail())) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setSubject("Invite new member to board!");
            helper.setTo(request.getEmail());
            helper.setText("BoardId: "+board.getId()+ "WorkSpaceId: "+workSpace.getId()+" Link : " + request.getLink());
            javaMailSender.send(mimeMessage);
            User user = userRepository.findUserByEmail(request.getEmail())
                    .orElseThrow(() -> new NotFoundException("User with email: " + request.getEmail() + " not found"));
            UserWorkSpaceRole userWorkSpace = new UserWorkSpaceRole();
            userWorkSpace.setMember(user);
            userWorkSpace.setRole(request.getRole());
            board.setWorkSpace(workSpace);
            board.getMembers().add(user);
            userWorkSpace.setWorkSpace(board.getWorkSpace());
            userWorkSpaceRoleRepository.save(userWorkSpace);
        } else throw new NotFoundException(String.format("User with email: %s is not found", request.getEmail()));
        log.info("Invitation sent to member with email: {} for board with id: {}", request.getEmail(), request.getBoardId());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Message sent successfully!")
                .build();
    }

    @Override
    public SimpleResponse changeMemberRole(ChangeRoleRequest request) {

        User admin = jwtService.getAuthentication();
        log.info("Changing member role in board with id: {}", request.boardId());
        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() -> {
                    log.error("Board with id: " + request.boardId() + " not found");
                    throw new NotFoundException("Board with id: " + request.boardId() + " not found");
                });
        User user = userRepository.findById(request.memberId())
                .orElseThrow(() -> {
                    log.error("User with id : " + request.memberId() + " not found");
                    throw new NotFoundException("User with id " + request.memberId() + " not found");
                });
        List<UserWorkSpaceRole> workSpaceRole = userWorkSpaceRoleRepository.findByUserId(board.getId(), user.getId());
        WorkSpace workSpace = board.getWorkSpace();
        if (!workSpace.getAdminId().equals(admin.getId())) {
            throw new BadCredentialException("You are not a admin of this workSpace");
        }
        for (UserWorkSpaceRole w : workSpaceRole) {
            for (Board b : w.getWorkSpace().getBoards()) {
                if (b.getId().equals(request.boardId())) {
                    w.setRole(request.role());
                    userWorkSpaceRoleRepository.save(w);
                    log.info("Member role changed successfully in board with id: {}", request.boardId());
                    return SimpleResponse.builder()
                            .status(HttpStatus.OK)
                            .message("Member role changed successfully!")
                            .build();
                }
            }
        }
        throw new NotFoundException("Board with id : " + request.boardId() + " not found in the workSpace");
    }

    @Override
    public List<MemberResponse> getAllMembersFromBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> {
                    log.error("Board with id: " + boardId + " not found");
                    throw new NotFoundException("Board with id: " + boardId + " not found");
                });
        return customMemberRepository.getAllMembersFromBoard(board.getId());
    }
    @Override
    public SimpleResponse assignMemberToCard(Long memberId, Long cardId) {

        User user = jwtService.getAuthentication();
        log.info("Assigning member with id: {} to card with id: {}", memberId, cardId);
        User newMember = userRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("User with id: %s not found".formatted(memberId)));
        Long adminId = cardRepository.getUserIdByCardId(cardId).orElseThrow(() -> {
                  log.error("This card with id: " + cardId + " is not present in your workspace!");
                   return new BadCredentialException("This card with id: " + cardId + " is not present in your workspace!");
              }
      );
      if (!user.getId().equals(adminId)) {
          log.error("You do not have permission to assign members to this card!");
          throw new BadCredentialException("You do not have permission to assign members to this card!");       }

        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new NotFoundException("Card with id: " + cardId + " not found!")
        );
        WorkSpace workSpace = card.getColumn().getBoard().getWorkSpace();

        Notification assignNotification = new Notification();
       assignNotification.setCard(card);
        assignNotification.setType(NotificationType.ASSIGN);
        assignNotification.setText(String.format("User with id %d has been assigned to card with id %d", memberId, cardId));
        assignNotification.setCreatedDate(ZonedDateTime.now());
        assignNotification.setIsRead(false);
       assignNotification.setFromUserId(workSpace.getAdminId());
        assignNotification.setColumnId(card.getColumn().getId());
        assignNotification.setBoardId(card.getColumn().getBoard().getId());
        card.getNotifications().add(assignNotification);
        newMember.getNotifications().add(assignNotification);
        notificationRepository.save(assignNotification);
        card.getMembers().add(newMember);
        newMember.getCards().add(card);
        userRepository.save(newMember);
        cardRepository.save(card);

        log.info("Member with id: {} successfully assigned to card with id: {}", memberId, cardId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
              .message(String.format("User with id : %d successfully assigned to card with id : %d", memberId, cardId))
               .build();
   }


    @Override
    public SimpleResponse removeMemberFromBoard(Long memberId, Long boardId) {

        User admin = jwtService.getAuthentication();
        log.info("Removing member with id: {} from board with id: {}", memberId, boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> {
                    log.error("Board with id: " + boardId + " not found");
                    throw new NotFoundException("Board with id: " + boardId + " not found");
                });
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("User with id : " + memberId + " not found");
                    throw new NotFoundException("User with id " + memberId + " not found");
                });
        WorkSpace workSpace = board.getWorkSpace();
        if (!workSpace.getAdminId().equals(admin.getId())) {
            log.error("User with id : {} is not an admin of this workSpace", admin.getId());
            throw new BadCredentialException("You are not a admin of this workSpace");
        }
        List<UserWorkSpaceRole> workSpaceRole = userWorkSpaceRoleRepository.findByUserId(board.getId(), user.getId());
        for (UserWorkSpaceRole w : workSpaceRole) {
            for (Board b : w.getWorkSpace().getBoards()) {
                if (b.getId().equals(boardId) && w.getMember().equals(user)) {
                    b.getMembers().remove(user);
                    userWorkSpaceRoleRepository.delete(w);
                    log.info("Member with id: {} removed from board with id: {}", memberId, boardId);
                }
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Member removed from board successfully!")
                        .build();
            }
        }
        throw new NotFoundException("User is not a member of the specified board.");
    }
}