package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.response.FavoriteBoardResponse;
import com.app.tasktracker.dto.response.FavoriteResponse;
import com.app.tasktracker.dto.response.FavoriteWorkSpaceResponse;
import com.app.tasktracker.models.User;
import com.app.tasktracker.repositories.customRepository.CustomFavoriteRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomFavoriteRepositoryImpl implements CustomFavoriteRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JwtService jwtService;

    @Override
    public FavoriteResponse getAll() {
        User user = jwtService.getAuthentication();

        String sql = """
                SELECT u.id AS user_id,
                       b.id AS board_id,
                       b.title,
                       b.back_ground
                       FROM users u
                       JOIN favorites f ON u.id = f.member_id
                       JOIN boards b ON f.board_id = b.id
                       WHERE u.id = ?
                """;
        List<FavoriteBoardResponse> boardResponses = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new FavoriteBoardResponse(
                        rs.getLong("user_id"),
                        rs.getLong("board_id"),
                        rs.getString("title"),
                        rs.getString("back_ground"),
                        true
                ),
                user.getId());
        String sql1 = """
                SELECT u.id AS user_id,
                       ws.id AS work_space_id,
                       ws.name
                       FROM users u
                       JOIN favorites f ON u.id = f.member_id
                       JOIN work_spaces ws on f.work_space_id = ws.id
                       WHERE u.id = ?
                     """;
        List<FavoriteWorkSpaceResponse> workSpaceResponses = jdbcTemplate.query(
                sql1,
                (rs, rowNum) -> new FavoriteWorkSpaceResponse(
                        rs.getLong("user_id"),
                        rs.getLong("work_space_id"),
                        rs.getString("name"),
                        true
                ),
                user.getId());
        return FavoriteResponse
                .builder()
                .workSpaceResponses(workSpaceResponses)
                .boardResponses(boardResponses)
                .build();
    }
}