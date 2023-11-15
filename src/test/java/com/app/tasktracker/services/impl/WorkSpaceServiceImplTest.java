package com.app.tasktracker.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.request.WorkSpaceRequest;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.dto.response.WorkSpaceFavoriteResponse;
import com.app.tasktracker.dto.response.WorkSpaceResponse;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.*;
import com.app.tasktracker.repositories.*;
import com.app.tasktracker.repositories.customRepository.CustomWorkSpaceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class WorkSpaceServiceImplTest {
    @InjectMocks
    private WorkSpaceServiceImpl workSpaceService;
    @Mock
    private WorkSpaceRepository workSpaceRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private CustomWorkSpaceRepository workSpaceJdbcTemplateService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private NotificationRepository notificationRepository;


    @BeforeEach
    void setUp() {
        workSpaceService = new WorkSpaceServiceImpl(
                workSpaceRepository,
                jwtService,
                userRepository,
                javaMailSender,
                workSpaceJdbcTemplateService,
                cardRepository,
                notificationRepository
        );

    }

    @Test
    void getAllWorkSpaces() {
        List<WorkSpaceResponse> expectedWorkSpaces = new ArrayList<>();
        WorkSpaceResponse workSpace1 = new WorkSpaceResponse(1L, "WorkSpace1");
        WorkSpaceResponse workSpace2 = new WorkSpaceResponse(2L, "WorkSpace2");
        expectedWorkSpaces.add(workSpace1);
        expectedWorkSpaces.add(workSpace2);

        when(workSpaceJdbcTemplateService.getAllWorkSpaces()).thenReturn(expectedWorkSpaces);

        List<WorkSpaceResponse> actualWorkSpaces = workSpaceService.getAllWorkSpaces();

        verify(workSpaceJdbcTemplateService, times(1)).getAllWorkSpaces();
        assertEquals(expectedWorkSpaces, actualWorkSpaces);
    }

    @Test
    void saveWorkSpace() throws MessagingException {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setWorkSpaces(new ArrayList<>());

        when(jwtService.getAuthentication()).thenReturn(mockUser);

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        WorkSpaceRequest request = new WorkSpaceRequest();
        request.setName("Test WorkSpace");
        request.setEmails(Collections.singletonList("test@example.com"));
        request.setLink("https://example.com");
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        doNothing().when(javaMailSender).send(mimeMessage);


        WorkSpaceFavoriteResponse response = workSpaceService.saveWorkSpace(request);
        verify(workSpaceRepository, times(1)).save(any());
        assertEquals("Test WorkSpace", response.getName());
        assertFalse(response.isFavorite());
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void getWorkSpaceById() {
        when(workSpaceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () ->
            workSpaceService.getWorkSpaceById(1L)
        );
        verify(workSpaceJdbcTemplateService, never()).getWorkSpaceById(anyLong());
    }

    @Test
    void updateWorkSpaceById() {
        User mockUser = new User();
        mockUser.setId(1L);
        when(jwtService.getAuthentication()).thenReturn(mockUser);
        WorkSpaceRequest request = new WorkSpaceRequest();
        request.setName("Updated WorkSpace Name");

        WorkSpace existingWorkSpace = new WorkSpace();
        existingWorkSpace.setId(1L);
        existingWorkSpace.setAdminId(1L);
        when(workSpaceRepository.getWorkSpaceByAdminIdAndId(eq(1L), eq(1L))).thenReturn(Optional.of(existingWorkSpace));
        SimpleResponse response = workSpaceService.updateWorkSpaceById(1L, request);
        verify(workSpaceRepository, times(1)).save(existingWorkSpace);
        assertEquals("Updated WorkSpace Name", existingWorkSpace.getName());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void testDeleteWorkSpaceById() {
        Long workspaceId = 1L;
        User user = new User();
        user.setId(1L);
        WorkSpace workSpace = new WorkSpace();
        workSpace.setId(workspaceId);
        workSpace.setMembers(List.of(user));
        List<User> members = new ArrayList<>();
        members.add(user);
        workSpace.setMembers(members);
        List<Board> boards = new ArrayList<>();
        Board board = new Board();
        Column column = new Column();
        Card card = new Card();
        card.setId(1L);
        Notification notification = new Notification();
        notification.setCard(card);

        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        column.setCards(cards);
        List<Column> columns = new ArrayList<>();
        columns.add(column);
        board.setColumns(columns);
        boards.add(board);
        workSpace.setBoards(boards);

        when(jwtService.getAuthentication()).thenReturn(user);
        when(workSpaceRepository.getWorkSpaceByAdminIdAndId(user.getId(), workspaceId)).thenReturn(Optional.of(workSpace));
        doNothing().when(cardRepository).deleteAll(anyList());
        when(notificationRepository.findByCardIn(cards)).thenReturn(notifications);

        SimpleResponse response = workSpaceService.deleteWorkSpaceById(workspaceId);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("WorkSpace with id " + workspaceId + " successfully deleted!", response.getMessage());

        verify(userRepository, times(1)).saveAll(members);
        verify(workSpaceRepository, times(1)).delete(workSpace);
        verify(cardRepository, times(cards.size())).deleteAll(cards);
        verify(notificationRepository, times(1)).deleteAll(notifications);
        assertNull(notification.getCard());
    }
}