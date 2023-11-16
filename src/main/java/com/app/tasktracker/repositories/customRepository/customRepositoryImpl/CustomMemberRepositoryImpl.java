package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.dto.response.MemberResponse;
import com.app.tasktracker.enums.Role;
import com.app.tasktracker.repositories.customRepository.CustomMemberRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CustomMemberRepositoryImpl implements CustomMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MemberResponse> getAllMembersByCardId(Long cardId) {
        String sql = """
                  SELECT
                      u.id ,
                      u.first_name,
                      u.last_name,
                      u.email,
                      u.image,
                      uwsr.role
                  FROM users u
                  JOIN cards_members cu ON u.id = cu.members_id
                  JOIN user_work_space_roles uwsr ON u.id = uwsr.member_id
                  WHERE cu.cards_id = ?
                  GROUP BY u.id,
                      u.first_name, u.last_name, u.email, u.image, u.role, uwsr.role;
                """;
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new MemberResponse(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("image"),
                        Role.valueOf(rs.getString("role"))
                ),
                cardId
        );
    }

    @Override
    public List<MemberResponse> searchByEmail(Long workSpaceId, String email) {
        String sql = """
                SELECT u.*
                FROM users u
                JOIN users_work_spaces uwsr ON uwsr.work_spaces_id = ?
                AND uwsr.members_id = u.id
                WHERE  lower(concat(u.first_name, u.last_name, u.email)) LIKE lower(concat('%',?,'%'));
                  """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new MemberResponse(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("image"),
                        Role.valueOf(rs.getString("role"))),
                workSpaceId,
                email
        );
    }

    @Override
    public List<MemberResponse> getAllMembersFromBoard(Long boardId) {
        String sql = """
                SELECT
                    u.id,
                    u.first_name,
                    u.last_name,u.email,
                    u.image,
                    uwsr.role
                FROM user_work_space_roles uwsr
                LEFT JOIN users u ON u.id = uwsr.member_id
                LEFT JOIN boards_members bm ON u.id = bm.members_id
                WHERE bm.boards_id = ?
             GROUP BY u.id,
                 u.first_name, u.last_name, u.email, u.image, u.role, uwsr.role;
                  """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new MemberResponse(
                                rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email"),
                                rs.getString("image"),
                                Role.valueOf(rs.getString("role")))
                , boardId);
    }
}