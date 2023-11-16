package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.dto.response.ColumnResponse;
import com.app.tasktracker.repositories.customRepository.CustomColumnRepository;

import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomColumnRepositoryImpl implements CustomColumnRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CustomCardJdbcTemplateServiceImpl service;

    @Override
    public List<ColumnResponse> getAllColumns(Long boardId) {
        String sql = """
                SELECT c.id, c.title, c.is_archive, c.board_id AS boardId
                FROM columns c
                LEFT JOIN cards card ON c.id = card.column_id
                WHERE c.board_id = ?
                group by c.id, c.title, c.is_archive, c.board_id
                ORDER BY c.id
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long columnId = rs.getLong("id");
            String title = rs.getString("title");
            Boolean isArchive = rs.getBoolean("is_archive");
            Long fetchedBoardId = rs.getLong("boardId");
            return new ColumnResponse(columnId, title, isArchive, fetchedBoardId, service.getAllCardsByColumnId(rs.getLong("id")));
        }, boardId);
    }
}