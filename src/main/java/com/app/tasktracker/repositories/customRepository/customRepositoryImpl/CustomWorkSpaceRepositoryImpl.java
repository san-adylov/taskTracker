package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.response.WorkSpaceResponse;
import com.app.tasktracker.models.User;
import com.app.tasktracker.repositories.customRepository.CustomWorkSpaceRepository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
@Getter
public class CustomWorkSpaceRepositoryImpl implements CustomWorkSpaceRepository {
    private final JwtService jwtService;
    private final JdbcTemplate jdbcTemplate;

    public List<WorkSpaceResponse> getAllWorkSpaces() {
        User user = jwtService.getAuthentication();
        String sql = """
                     SELECT admin.id AS user_id,
                            combined_result.id AS id,
                            work_space_name,
                            is_favorite,
                            CONCAT(admin.first_name, '  ', admin.last_name) AS full_name,
                            admin.image AS image
                     FROM (
                              SELECT ws.id AS id,
                              ws.name AS work_space_name,
                              CASE WHEN f.work_space_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_favorite,
                              ws.admin_id AS admin_id
                              FROM work_spaces ws
                              JOIN users_work_spaces uws ON ws.id = uws.work_spaces_id
                              JOIN users u ON uws.members_id = u.id
                              LEFT JOIN favorites f ON ws.id = f.work_space_id AND u.id = f.member_id
                              WHERE u.id = ?
                              UNION
                              SELECT ws.id AS id,
                              ws.name AS work_space_name,
                              CASE WHEN f.work_space_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_favorite,
                              ws.admin_id AS admin_id
                              FROM work_spaces ws
                              JOIN user_work_space_roles uwsr ON ws.id = uwsr.work_space_id
                              JOIN users u ON uwsr.member_id=u.id
                              LEFT JOIN favorites f ON ws.id = f.work_space_id AND u.id = f.member_id
                              WHERE u.id = ?
                          ) AS combined_result
                              LEFT JOIN users admin ON combined_result.admin_id = admin.id;
                     
                       """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> WorkSpaceResponse
                        .builder()
                        .workSpaceId(rs.getLong("id"))
                        .workSpaceName(rs.getString("work_space_name"))
                        .adminId(rs.getLong("user_id"))
                        .adminFullName(rs.getString("full_name"))
                        .adminImage(rs.getString("image"))
                        .isFavorite(rs.getBoolean("is_favorite"))
                        .build(),
                user.getId(), user.getId());
    }

    @Override
    public WorkSpaceResponse getWorkSpaceById(Long workSpaceId) {
        String sql = """
                SELECT w.id                                                AS workSpaceId,
                       w.name                                              AS workSpaceName,
                       u.id                                                AS userId,
                       CONCAT(u.first_name, ' ', u.last_name)              AS fullName,
                       u.image                                             AS image,
                       CASE WHEN f.id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavorite
                FROM work_spaces w
                         JOIN users u ON w.admin_id = u.id
                         LEFT JOIN favorites f ON w.id = f.work_space_id AND u.id = f.member_id
                WHERE w.id = ?;
                    """;
        List<WorkSpaceResponse> result = jdbcTemplate.query(
                sql,
                new Object[]{workSpaceId},
                (rs, rowNum) -> {
                    WorkSpaceResponse response = new WorkSpaceResponse();
                    response.setWorkSpaceId(rs.getLong("workSpaceId"));
                    response.setWorkSpaceName(rs.getString("workSpaceName"));
                    response.setAdminId(rs.getLong("userId"));
                    response.setAdminFullName(rs.getString("fullName"));
                    response.setAdminImage(rs.getString("image"));
                    response.setIsFavorite(rs.getBoolean("isFavorite"));
                    return response;
                }
        );
        return result.get(0);
    }

}