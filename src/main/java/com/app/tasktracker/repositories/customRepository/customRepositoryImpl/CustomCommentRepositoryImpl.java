package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.response.CommentResponse;
import com.app.tasktracker.models.User;
import com.app.tasktracker.repositories.customRepository.CustomCommentRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JwtService jwtService;

    @Override
    public List<CommentResponse> getAllUserComments(Long userId) {
        String sqlQuery = """
                SELECT c.id                                    AS id,
                       c.comment                               AS comment,
                       c.created_date                          AS date,
                       u.id                                    AS creatorId,
                       concat(u.first_name, '  ', u.last_name) AS fullName,
                       u.image                                 AS avatar,
                       CASE WHEN u.id = cu.members_id THEN TRUE ELSE FALSE END AS isMine
                FROM comments AS c
                         JOIN users u ON u.id = c.member_id
                         LEFT JOIN cards_members cu ON u.id = cu.members_id
                WHERE u.id= ? group by c.id, c.comment, c.created_date, u.id,
                concat(u.first_name, '  ', u.last_name), u.image,
                CASE WHEN u.id = cu.members_id THEN TRUE ELSE FALSE END;
                 """;
        return jdbcTemplate.query(sqlQuery, new Object[]{userId}, new CommentResponseRowMapperr());
    }

    private class CommentResponseRowMapperr implements RowMapper<CommentResponse> {
        @Override
        public CommentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(rs.getLong("id"));
            commentResponse.setComment(rs.getString("comment"));
            commentResponse.setCreatorName(rs.getString("fullName"));
            commentResponse.setCreatorId(rs.getLong("creatorId"));
            commentResponse.setCreatorAvatar(rs.getString("avatar"));
            commentResponse.setIsMyComment(rs.getBoolean("isMine"));
            java.sql.Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                ZonedDateTime zonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy / h:mma");
                String formattedDateTime = zonedDateTime.format(formatter);
                commentResponse.setCreatedDate(formattedDateTime);
            }
            return commentResponse;
        }
    }

    @Override
    public List<CommentResponse> getAllCommentByCardId(Long cardId) {
        User user = jwtService.getAuthentication();
        String sqlQuery = """
                SELECT DISTINCT c.id                            AS id,
                       c.comment                                AS comment,
                       c.created_date                           AS date,
                       u.id                                     AS creatorId,
                       concat(u.first_name, '  ', u.last_name) AS fullName,
                       u.image                                  AS image,
                       CASE WHEN u.id = ? THEN TRUE ELSE FALSE END AS isMine
                FROM comments AS c
                         JOIN users AS u ON u.id = c.member_id
                         JOIN comments com on u.id = com.member_id
                         JOIN cards AS c2 ON c2.id = c.card_id
                WHERE c2.id= ?;
                       """;
        return jdbcTemplate.query
                (sqlQuery, new Object[]{user.getId(), cardId}, new CommentResponseRowMapperer());
    }

    private class CommentResponseRowMapperer implements RowMapper<CommentResponse> {
        @Override
        public CommentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(rs.getLong("id"));
            commentResponse.setComment(rs.getString("comment"));
            commentResponse.setCreatorName(rs.getString("fullName"));
            commentResponse.setCreatorId(rs.getLong("creatorId"));
            commentResponse.setCreatorAvatar(rs.getString("image"));
            commentResponse.setIsMyComment(rs.getBoolean("isMine"));
            java.sql.Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                ZonedDateTime zonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy / h:mma");
                String formattedDateTime = zonedDateTime.format(formatter);
                commentResponse.setCreatedDate(formattedDateTime);
            }
            return commentResponse;
        }
    }

    @Override
    public List<CommentResponse> getAllComments() {
        User user = jwtService.getAuthentication();
        String sqlQuery = """
                SELECT DISTINCT c.id                             AS id,
                       c.comment                                 AS comment,
                       c.created_date                           AS date,
                       u.id                                     AS creatorId,
                       concat(u.first_name, '   ', u.last_name) AS fullName,
                       u.image                                  AS image,
                       CASE WHEN u.id = ? THEN TRUE ELSE FALSE END AS isMine
                FROM comments AS c
                         JOIN users AS u ON u.id = c.member_id
                    """;
        return jdbcTemplate.query
                (sqlQuery, new Object[]{user. getId()}, new CommentResponseRowMap());
    }

    private class CommentResponseRowMap implements RowMapper<CommentResponse> {
        @Override
        public CommentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(rs.getLong("id"));
            commentResponse.setComment(rs.getString("comment"));
            commentResponse.setCreatorName(rs.getString("fullName"));
            commentResponse.setCreatorId(rs.getLong("creatorId"));
            commentResponse.setCreatorAvatar(rs.getString("image"));
            commentResponse.setIsMyComment(rs.getBoolean("isMine"));
            java.sql.Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                ZonedDateTime zonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy / h:mma");
                String formattedDateTime = zonedDateTime.format(formatter);
                commentResponse.setCreatedDate(formattedDateTime);
            }
            return commentResponse;
        }
    }

    @Override
    public CommentResponse getCommentById(Long commentId) {
        User user = jwtService.getAuthentication();
        String sqlQuery = """
                SELECT c.id                                     AS id,
                       c.comment                                AS comment,
                       c.created_date                           AS date,
                       u.id                                     AS creatorId,
                       concat(u.first_name, '   ', u.last_name) AS fullName,
                       u.image                                  AS image,
                       CASE WHEN u.id = ? THEN TRUE ELSE FALSE END AS isMine
                FROM comments AS c
                         JOIN users u ON u.id = c.member_id
                    AND c.id = ?
                  """;
        return jdbcTemplate.queryForObject
                (sqlQuery, new Object[]{user.getId(), commentId}, new CommentResponseRowMapper());
    }

    private class CommentResponseRowMapper implements RowMapper<CommentResponse> {
        @Override
        public CommentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(rs.getLong("id"));
            commentResponse.setComment(rs.getString("comment"));
            commentResponse.setCreatorName(rs.getString("fullName"));
            commentResponse.setCreatorId(rs.getLong("creatorId"));
            commentResponse.setCreatorAvatar(rs.getString("image"));
            commentResponse.setIsMyComment(rs.getBoolean("isMine"));
            java.sql.Timestamp timestamp = rs.getTimestamp("date");
            if (timestamp != null) {
                ZonedDateTime zonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy / h:mma");
                String formattedDateTime = zonedDateTime.format(formatter);
                commentResponse.setCreatedDate(formattedDateTime);
            }
            return commentResponse;
        }
    }
}
