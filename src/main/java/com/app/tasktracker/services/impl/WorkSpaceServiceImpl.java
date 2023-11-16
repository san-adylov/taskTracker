package com.app.tasktracker.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.request.WorkSpaceRequest;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.dto.response.WorkSpaceFavoriteResponse;
import com.app.tasktracker.dto.response.WorkSpaceResponse;
import com.app.tasktracker.enums.Role;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.*;
import com.app.tasktracker.repositories.*;
import com.app.tasktracker.repositories.customRepository.CustomWorkSpaceRepository;
import com.app.tasktracker.services.WorkSpaceService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkSpaceServiceImpl implements WorkSpaceService {

    private final WorkSpaceRepository workSpaceRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final CustomWorkSpaceRepository workSpaceJdbcTemplateService;
    private final CardRepository cardRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public List<WorkSpaceResponse> getAllWorkSpaces() {
        return workSpaceJdbcTemplateService.getAllWorkSpaces();
    }
    @Override
    public WorkSpaceFavoriteResponse saveWorkSpace(WorkSpaceRequest request) throws MessagingException {
        User user = jwtService.getAuthentication();
        WorkSpace workspace = new WorkSpace(request.getName(), user.getId());
        UserWorkSpaceRole userWorkSpace = new UserWorkSpaceRole(Role.ADMIN, user, workspace);
        workspace.setMembers(List.of(user));
        workspace.setCreatedDate(ZonedDateTime.now());
        workSpaceRepository.save(workspace);
        List<WorkSpace> existingWorkSpaces = user.getWorkSpaces();
        existingWorkSpaces.add(workspace);
        user.setWorkSpaces(existingWorkSpaces);
        List<String> invitationEmails = request.getEmails();

        if (!invitationEmails.isEmpty() && !invitationEmails.get(0).isBlank()) {
            for (String email : invitationEmails) {
                if (!userRepository.existsByEmail(email)) {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setSubject("Welcome to my workspace");
                    helper.setFrom("tasktrackerjava9@gmail.com");
                    helper.setTo(email);
                    helper.setText("/workspaceId/" + workspace.getId() + " Click link to register :" + request.getLink());
                    javaMailSender.send(mimeMessage);
                }
            }
        }

        log.info(String.format("WorkSpace with name %s successfully saved!", workspace.getName()));
        log.info("Workspace is saved!");

        return WorkSpaceFavoriteResponse.builder()
                .workSpaceId(workspace.getId())
                .name(workspace.getName())
                .isFavorite(false)
                .build();
    }

    @Override
    public WorkSpaceResponse getWorkSpaceById(Long id) {
        workSpaceRepository.findById(id).orElseThrow(() -> {
            log.error("WorkSpace with id " + id + " not found ! ");
            return new NotFoundException("WorkSpace with id " + id + " not found ! ");
        });
        return workSpaceJdbcTemplateService.getWorkSpaceById(id);
    }

    @Override
    public SimpleResponse updateWorkSpaceById(Long id, WorkSpaceRequest workSpaceRequest) {
        User user = jwtService.getAuthentication();
        WorkSpace workSpace = workSpaceRepository.getWorkSpaceByAdminIdAndId(user.getId(), id)
                .orElseThrow(() -> {
                    log.error("WorkSpace with id " + id + " not found ! ");
                    return new NotFoundException("WorkSpace with id " + id + " not found ! ");
                });
        workSpace.setName(workSpaceRequest.getName());
        workSpaceRepository.save(workSpace);
        log.info(String.format("WorkSpace with id %s  successfully updated !", id));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("WorkSpace with id %s  successfully updated !", id))
                .build();
    }

    @Override
    public SimpleResponse deleteWorkSpaceById(Long id) {
        try {
            User user = jwtService.getAuthentication();
            WorkSpace workSpace = workSpaceRepository.getWorkSpaceByAdminIdAndId(user.getId(), id)
                    .orElseThrow(() -> {
                        log.error("You can't delete this workSpace with id: " + id + " !");
                        return new NotFoundException("You can't delete this workSpace with id: " + id + " !");
                    });
            List<User> usersToRemoveWorkspace = new ArrayList<>(workSpace.getMembers());
            for (User user1 : workSpace.getMembers()) {
                if (user1.getWorkSpaces() != null) {
                    user1.getWorkSpaces().remove(workSpace);
                }
            }
            for (Board board : workSpace.getBoards()) {
                for (Column c : board.getColumns()) {
                    List<Card> cardsToDelete = new ArrayList<>(c.getCards());
                    removeNotificationsForCards(cardsToDelete);
                    c.getCards().removeAll(cardsToDelete);
                    cardRepository.deleteAll(cardsToDelete);
                }
            }
            userRepository.saveAll(usersToRemoveWorkspace);
            workSpaceRepository.delete(workSpace);
            log.info(String.format("WorkSpace with id %s successfully deleted!", id));
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("WorkSpace with id %s successfully deleted!", id))
                .build();
    }

    private void removeNotificationsForCards(List<Card> cards) {
        List<Notification> notifications = notificationRepository.findByCardIn(cards);
        notifications.forEach(notification -> notification.setCard(null));
        notificationRepository.deleteAll(notifications);
    }
}