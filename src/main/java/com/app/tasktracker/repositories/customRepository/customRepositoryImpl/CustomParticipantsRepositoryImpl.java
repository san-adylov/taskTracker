package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.response.ParticipantsGetAllResponse;
import com.app.tasktracker.dto.response.ParticipantsResponse;
import com.app.tasktracker.enums.Role;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.models.User;
import com.app.tasktracker.repositories.customRepository.CustomParticipantsRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CustomParticipantsRepositoryImpl implements CustomParticipantsRepository {

    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;

    @Override
    public ParticipantsGetAllResponse getParticipantsByRole(Long workSpaceId, Role role) {
        if (workSpaceId == null) {
            throw new NotFoundException("WorkSpace ID cannot be null");
        }

        User user = jwtService.getAuthentication();
        String sql;
        Object[] params;

        if (role==Role.ALL) {
            sql = """
        SELECT u.id AS id, 
        CONCAT(u.first_name, ' ', u.last_name) AS fullname,
        u.email AS email,
        u.image as image,
        uwsr.role AS role,
        ? AS isAdmin
        FROM user_work_space_roles uwsr
        JOIN work_spaces ws ON ws.id = uwsr.work_space_id
        JOIN users u ON uwsr.member_id = u.id
        WHERE ws.id = ? AND (uwsr.role = ? OR uwsr.role = ?) AND u.id != ws.admin_id; 
        """;
        params = new Object[]{user.getId().equals(getWorkspaceCreatorId(workSpaceId)), workSpaceId, Role.ADMIN.toString(), Role.MEMBER.toString()};
}else {
            sql = """
                    SELECT u.id AS id,
                    CONCAT(u.first_name, ' ', u.last_name) AS fullname,
                    u.email AS email,
                    u.image as image,
                    uwsr.role AS role,
                    ? AS isAdmin
                    FROM user_work_space_roles uwsr
                    JOIN work_spaces ws ON ws.id = uwsr.work_space_id
                    JOIN users u ON uwsr.member_id = u.id
                    WHERE ws.id = ? AND uwsr.role = ? AND u.id != ws.admin_id;  
                    """;
            params = new Object[]{user.getId().equals(getWorkspaceCreatorId(workSpaceId)), workSpaceId,role.toString()};

        }
        List<ParticipantsResponse> participantsResponses = jdbcTemplate.query(sql, params, (rs, rowNum) ->
                new ParticipantsResponse(
                        rs.getLong("id"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("image"),
                        Role.valueOf(rs.getString("role")),
                        rs.getBoolean("isAdmin")
                ));

        return ParticipantsGetAllResponse.builder()
                .participantsResponseList(participantsResponses)
                .isMy(user.getId().equals(getWorkspaceCreatorId(workSpaceId)))
                .build();
    }
    private Long getWorkspaceCreatorId(Long workSpaceId) {
        String sql = "SELECT admin_id FROM work_spaces WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, workSpaceId);
    }

}
