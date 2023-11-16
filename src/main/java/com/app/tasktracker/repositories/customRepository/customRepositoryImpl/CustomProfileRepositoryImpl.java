package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.request.UserRequest;
import com.app.tasktracker.dto.response.*;
import com.app.tasktracker.models.User;
import com.app.tasktracker.repositories.customRepository.CustomProfileRepository;

import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class CustomProfileRepositoryImpl implements CustomProfileRepository {

    private final JdbcTemplate jdbcTemplate;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = jwtService.getAuthentication();
        String query = "UPDATE users AS u SET first_name=?,last_name=?,email=?,password=? WHERE u.id=?";

        jdbcTemplate.update(query,
                userRequest.firstName(),
                userRequest.lastName(),
                userRequest.email(),
                passwordEncoder.encode(userRequest.password()),
                user.getId());

        return UserResponse.builder()
                .userId(user.getId())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .avatar(user.getImage())
                .build();
    }

    @Override
    public ProfileResponse getProfileById(Long userId) {

        String query = """
                SELECT u.id, email, first_name, image, last_name
                FROM users u
                WHERE u.id = ?
                """;
        User user = jdbcTemplate.queryForObject(query, (rs, rowNum) ->
                        new User(rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email"),
                                rs.getString("image")
                        ),
                userId);
        String query1 = """
                SELECT ws.id,ws.name
                FROM users u
                         JOIN users_work_spaces uws ON u.id = uws.members_id
                         JOIN work_spaces ws ON uws.work_spaces_id = ws.id
                WHERE u.id = ?;
                        """;
        List<WorkSpaceResponse> workSpaceResponses = jdbcTemplate.query(query1, (rs, rowNum) -> new WorkSpaceResponse(
                rs.getLong("id"),
                rs.getString("name")
        ), userId);
        String sql = """
                       SELECT\s
                           u.id AS userId,
                           u.first_name AS firstName,
                           u.last_name AS lastName,
                           u.email AS email,
                           u.image AS avatar,
                           (SELECT COUNT(*)\s
                            FROM users AS u2

                            JOIN users_work_spaces uws ON u2.id = uws.members_id
                            JOIN work_spaces ws ON ws.id = uws.work_spaces_id
                WHERE u2.id = u.id) AS countWorkSpaces
                       FROM users AS u
                       WHERE u.id = ?;
                        
                                         """;
        ProfileResponse profileResponse = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ProfileResponse(rs.getLong("userId")
                , rs.getString("firstName")
                , rs.getString("lastName")
                , rs.getString("email")
                , rs.getString("avatar")
                , rs.getInt("countWorkSpaces")
        ), userId);
        assert profileResponse != null;
        int count = profileResponse.getCountWorkSpaces();
        profileResponse.setCountWorkSpaces(count);
        assert user != null;
        return ProfileResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .avatar(user.getImage())
                .countWorkSpaces(count)
                .workSpaceResponse(workSpaceResponses)
                .build();
    }
    @Override
    public ProfileResponse getMyProfile() {

        User user = jwtService.getAuthentication();

        String workSpaceQuery = """
            SELECT ws.id, ws.name
            FROM users u
                     JOIN users_work_spaces uws ON u.id = uws.members_id
                     JOIN work_spaces ws ON uws.work_spaces_id = ws.id
            WHERE u.id = ?;
                    """;
        List<WorkSpaceResponse> workSpaceResponses = jdbcTemplate.query(workSpaceQuery, (rs, rowNum) -> new WorkSpaceResponse(
                rs.getLong("id"),
                rs.getString("name")
        ), user.getId());

        String profileQuery = """
            SELECT
                u.id AS userId,
                u.first_name AS firstName,
                u.last_name AS lastName,
                u.email AS email,
                u.image AS avatar,
                (SELECT COUNT(*)
                 FROM users AS u2
                 JOIN users_work_spaces uws ON u2.id = uws.members_id
                 JOIN work_spaces ws ON ws.id = uws.work_spaces_id
                 WHERE u2.id = u.id) AS countWorkSpaces
            FROM users AS u
            WHERE u.id = ?;
            """;

        ProfileResponse profileResponse = jdbcTemplate.queryForObject(profileQuery, (rs, rowNum) -> new ProfileResponse(
                rs.getLong("userId"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("avatar"),
                rs.getInt("countWorkSpaces")
        ), user.getId());

        assert profileResponse != null;
        int count = profileResponse.getCountWorkSpaces();
        profileResponse.setCountWorkSpaces(count);
        profileResponse.setWorkSpaceResponse(workSpaceResponses);

        return profileResponse;
    }

    @Override
    public GlobalSearchResponse search(String search) {
        User user = jwtService.getAuthentication();

        if (user != null && user.getId() != null) {
            String sql = """
                    SELECT u.id, u.email, u.first_name, u.image, u.last_name
                    FROM users u
                    LEFT JOIN user_work_space_roles uwsr ON u.id = uwsr.member_id
                    LEFT JOIN users_work_spaces uws ON u.id = uws.members_id
                    WHERE (u.first_name ILIKE CONCAT('%',?, '%') OR u.last_name ILIKE CONCAT('%',?,'%'))
                    AND (uwsr.work_space_id IN (SELECT w.id FROM work_spaces w WHERE w.admin_id = ?)
                    OR uws.work_spaces_id IN (SELECT w.id FROM work_spaces w WHERE w.admin_id = ?))
                    group by u.id, u.email, u.first_name, u.image, u.last_name;
                    """;
            List<UserResponse> userResponses = jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponse(rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("image")), search, search, user.getId(),user.getId());

            String sql2 = """   
                    SELECT b.work_space_id, b.id, back_ground, title FROM boards b
                    WHERE (b.title ILIKE (CONCAT('%',?,'%')))
                    AND b.work_space_id IN (
                    SELECT w.id FROM work_spaces w WHERE w.admin_id = ?)
                    GROUP BY b.work_space_id, b.id, back_ground, title
                      """;
            List<BoardResponse> boardResponses = jdbcTemplate.query(sql2, (rs, rowNum) -> new BoardResponse(rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("back_ground"),
                    rs.getLong("work_space_id")), search, user.getId());

            String sql4 = """
                   SELECT w.admin_id, CONCAT(u.first_name, ' ', u.last_name) AS fullName, u.image, w.id, name,
                   CASE WHEN f.work_space_id IS NOT NULL THEN TRUE ELSE FALSE END AS isFavorite
                   FROM work_spaces w
                            left JOIN users_work_spaces uws ON w.id = uws.work_spaces_id
                            left JOIN users u ON u.id = uws.members_id
                            left join favorites f on w.id = f.work_space_id
                   WHERE (w.name ILIKE (CONCAT('%', ?, '%')))
                   AND w.admin_id = ?
                   GROUP by u.image, CONCAT(u.first_name, ' ', u.last_name), w.admin_id, w.id, name,
                   CASE WHEN f.work_space_id IS NOT NULL THEN TRUE ELSE FALSE END
                     """;
            List<WorkSpaceResponse> workSpaceResponses = jdbcTemplate.query(sql4, (rs, rowNum) -> WorkSpaceResponse.builder()
                    .workSpaceId(rs.getLong("id"))
                    .adminFullName(rs.getString("fullName"))
                    .adminImage(rs.getString("image"))
                    .adminId(rs.getLong("admin_id"))
                    .workSpaceName(rs.getString("name"))
                    .isFavorite(rs.getBoolean("isFavorite"))
                    .build(), search, user.getId());

            return GlobalSearchResponse.builder()
                    .userResponses(userResponses)
                    .boardResponses(boardResponses)
                    .workSpaceResponses(workSpaceResponses)
                    .build();
        } else {
            return GlobalSearchResponse.builder().build();
        }
    }
}