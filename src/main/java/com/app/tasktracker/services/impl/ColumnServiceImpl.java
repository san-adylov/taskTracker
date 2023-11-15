package com.app.tasktracker.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.request.ColumnRequest;
import com.app.tasktracker.dto.response.ColumnResponse;
import com.app.tasktracker.dto.response.SimpleResponse;
import com.app.tasktracker.enums.Role;
import com.app.tasktracker.exceptions.BadCredentialException;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.*;
import com.app.tasktracker.repositories.*;
import com.app.tasktracker.repositories.customRepository.customRepositoryImpl.CustomColumnRepositoryImpl;
import com.app.tasktracker.services.ColumnService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ColumnServiceImpl implements ColumnService {

    private final ColumnsRepository columnsRepository;

    private final BoardRepository boardRepository;

    private final CustomColumnRepositoryImpl columns;

    private final JwtService jwtService;

    private final WorkSpaceRepository workSpaceRepository;

    private final UserWorkSpaceRoleRepository userWorkSpaceRoleRepository;

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    @Override
    public SimpleResponse createColumn(ColumnRequest columnRequest) {
        User user = jwtService.getAuthentication();
        Board board = boardRepository.findById(columnRequest.boardId()).orElseThrow(() -> {
            log.error("Column with id: " + columnRequest.boardId() + " not found");
            return new NotFoundException("Column with id: " + columnRequest.boardId() + " not found");
        });
        Column column = new Column();
        if (user.getRole().equals(Role.ADMIN)) {
            column.setTitle(columnRequest.title());
            column.setIsArchive(false);
            board.getColumns().add(column);
            column.setBoard(board);
            user.getColumns().add(column);
            column.setMembers(List.of(user));
            columnsRepository.save(column);
            log.info("Successfully created");
            return SimpleResponse.builder()
                    .message("Successfully create column")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            throw new BadCredentialException("You are not member");
        }
    }

    @Override
    public List<ColumnResponse> getAllColumns(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            log.error("Board with id: " + boardId + " not found");
            return new NotFoundException("Board with id: " + boardId + " not found");
        });
        return columns.getAllColumns(board.getId());
    }

    @Override
    public ColumnResponse update(Long columnId, ColumnRequest columnRequest) {
        User user = jwtService.getAuthentication();
        Column column = columnsRepository.findById(columnId).orElseThrow(() -> {
            log.error("Column not found!");
            return new NotFoundException("Column with id: " + columnId + " not found");
        });
        if (user.getRole().equals(Role.ADMIN)) {
            column.setTitle(columnRequest.title());
            columnsRepository.save(column);
            log.info("Column successfully update");
        } else {
            throw new BadCredentialException("You are not member");
        }
        return new ColumnResponse(column.getId(), column.getTitle(), column.getIsArchive());
    }

    @Override
    public SimpleResponse removeColumn(Long columnId) {
        User user = jwtService.getAuthentication();
        Column column = columnsRepository.findById(columnId).orElseThrow(() -> {
            log.error("Column not found!");
            return new NotFoundException("Column with id: " + columnId + " not found");
        });
        if (user.getRole().equals(Role.ADMIN)) {
            columnsRepository.delete(column);
            log.info("Column  successfully deleted");
        } else throw new BadCredentialException("You are not member");
        return SimpleResponse.builder()
                .message("Column successfully deleted")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse sendToArchive(Long columnId) {
        Column column = columnsRepository.findById(columnId).orElseThrow(() -> {
            log.error("Column with id: " + columnId + " not found!");
            return new NotFoundException("Column with id: " + columnId + " not found!");
        });

         column.setIsArchive(!column.getIsArchive());
            columnsRepository.save(column);

            List<Card> cardsInColumn = new ArrayList<>(column.getCards());
            for (Card card : cardsInColumn) {
                card.setIsArchive(column.getIsArchive());
                cardRepository.save(card);
            }

            String message = column.getIsArchive() ? "Column with id: " + columnId + " successfully archived!" : "Column with id: " + columnId + " successfully unArchived!";
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(message)
                    .build();
    }
}