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
import com.app.tasktracker.dto.request.ParticipantsChangeRequest;
import com.app.tasktracker.dto.request.ParticipantsRequest;
import com.app.tasktracker.dto.response.ParticipantsGetAllResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.enums.Role;
import com.app.tasktracker.exceptions.BadCredentialException;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.User;
import com.app.tasktracker.models.UserWorkSpaceRole;
import com.app.tasktracker.models.WorkSpace;
import com.app.tasktracker.repositories.UserRepository;
import com.app.tasktracker.repositories.UserWorkSpaceRoleRepository;
import com.app.tasktracker.repositories.WorkSpaceRepository;
import com.app.tasktracker.repositories.customRepository.customRepositoryImpl.CustomParticipantsRepositoryImpl;
import com.app.tasktracker.services.ParticipantsService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ParticipantsServiceImpl implements ParticipantsService {

    private final UserWorkSpaceRoleRepository userWorkSpaceRoleRepository;

    private final CustomParticipantsRepositoryImpl customParticipantsRepository;

    private final WorkSpaceRepository workSpaceRepository;

    private final JavaMailSender javaMailSender;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Override
    public SimpleResponse inviteToWorkSpaces(ParticipantsRequest request) throws MessagingException {
        User authentication = jwtService.getAuthentication();
        WorkSpace workSpace = workSpaceRepository.findById(request.workSpacesId()).orElseThrow(() ->
                new NotFoundException("Workspace with id " + request.workSpacesId() + " not found"));

        if (!authentication.getWorkSpaces().contains(workSpace) || !authentication.getRole().equals(Role.ADMIN)) {
            throw new BadCredentialException("You are not authorized to invite members to this workspace");
        }
        User user = userRepository.findUserByEmailParticipants(request.email());
        if (user == null) {
            throw new NotFoundException("User with email " + request.email() + " not found");
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setSubject("Invite to workspace");
        helper.setTo(request.email());
        String invitationText = request.link() + "/" + request.role() + "/workspaceId/" + request.workSpacesId();
        helper.setText(invitationText);
        javaMailSender.send(mimeMessage);

        UserWorkSpaceRole userWorkSpaceRole = new UserWorkSpaceRole();
        userWorkSpaceRole.setMember(user);
        userWorkSpaceRole.setRole(request.role());
        userWorkSpaceRole.setWorkSpace(workSpace);
        userWorkSpaceRoleRepository.save(userWorkSpaceRole);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully invited")
                .build();
    }

    @Override
    public SimpleResponse participantsRemoveToWorkSpaces(Long workSpaceId,Long userId) {
        User authentication = jwtService.getAuthentication();
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new NotFoundException("Workspace with id " + workSpaceId + " not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        if (!workSpace.getAdminId().equals(authentication.getId())) {
            throw new NotFoundException("You are not the admin of this workspace");
        }
        List<UserWorkSpaceRole> userWorkSpaceRoles = userWorkSpaceRoleRepository.findByUserToWorkSpace(user.getId(), workSpace.getId());
        for (UserWorkSpaceRole workSpaceRole : userWorkSpaceRoles) {
            if (workSpaceRole.getWorkSpace().equals(workSpace) && workSpaceRole.getMember().equals(user)) {
                userWorkSpaceRoleRepository.deleteById(workSpaceRole.getId());
                log.info("Successfully deleted");
            } else {
                throw new BadCredentialException("Not found id");
            }
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }

    @Override
    public SimpleResponse changeUpdateRole(ParticipantsChangeRequest request) {
        User authentication = jwtService.getAuthentication();
        WorkSpace workSpace = workSpaceRepository.findById(request.workSpacesId())
                .orElseThrow(() -> new NotFoundException("Workspace with id " + request.workSpacesId() + " not found"));

        User user = userRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("User with id " + request.memberId() + " not found"));

        if (!workSpace.getAdminId().equals(authentication.getId())) {
            throw new NotFoundException("You are not the admin of this workspace");
        }
        List<UserWorkSpaceRole> userWorkSpaceRoles = userWorkSpaceRoleRepository.findByUserToWorkSpace(user.getId(), workSpace.getId());
        for (UserWorkSpaceRole userWorkSpaceRole : userWorkSpaceRoles) {
            if (userWorkSpaceRole.getWorkSpace().getId().equals(request.workSpacesId())) {
                userWorkSpaceRole.setRole(request.role());
                userWorkSpaceRoleRepository.save(userWorkSpaceRole);
                log.info("Updated change role");
            }
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully role updated")
                .build();
    }

    @Override
    public ParticipantsGetAllResponse getParticipantsByRole(Long workSpaceId, Role role) {
        return customParticipantsRepository.getParticipantsByRole(workSpaceId,role);
    }
}
