package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.config.security.JwtService;
import com.app.tasktracker.dto.response.*;
import com.app.tasktracker.enums.NotificationType;
import com.app.tasktracker.models.User;
import com.app.tasktracker.repositories.customRepository.CustomNotificationRepository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JwtService jwtService;

    @Override
    public List<NotificationResponse> getAllNotifications() {
        User user = jwtService.getAuthentication();
        String sql = """           
       SELECT n.id                                   AS notification_id,
              n.text                                 AS text,
              n.created_date                         AS created_date,
              n.is_read                              AS is_read,
              n.type                                 AS type,
              u.id                                   AS from_user_id,
              CONCAT(u.first_name, ' ', u.last_name) AS full_name,
              u.image                                AS image_user,
              b.id                                   AS board_id,
              b.title                                AS title_board,
              b.back_ground                          AS back_ground,
              c.id                                   AS column_id,
              c.title                                AS column_name,
              c2.id                                  AS card_id
       FROM notifications n
                left JOIN notifications_members nm ON n.id = nm.notifications_id
                 JOIN users u ON nm.members_id = u.id or n.from_user_id = u.id
                 JOIN boards b ON n.board_id = b.id
                 JOIN columns c ON n.column_id = c.id
                 JOIN cards c2 ON n.card_id = c2.id
       WHERE u.id = ? group by n.id, n.text, n.created_date,
       n.is_read, n.type, u.id, CONCAT(u.first_name, ' ', u.last_name),
       u.image, b.id, b.title, b.back_ground, c.id, c.title, c2.id;
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> NotificationResponse.builder()
                .notificationId(rs.getLong("notification_id"))
                .text(rs.getString("text"))
                .createdDate(rs.getString("created_date"))
                .isRead(rs.getBoolean("is_read"))
                .notificationType(NotificationType.valueOf(rs.getString("type")))
                .fromUserId(rs.getLong("from_user_id"))
                .fullName(rs.getString("full_name"))
                .imageUser(rs.getString("image_user"))
                .boardId(rs.getLong("board_id"))
                .titleBoard(rs.getString("title_board"))
                .backGround(rs.getString("back_ground"))
                .columnId(rs.getLong("column_id"))
                .columnName(rs.getString("column_name"))
                .cardId(rs.getLong("card_id"))
                .build()
        ,user.getId());
    }

    @Override
    public NotificationResponse getNotificationById(Long notificationId) {
            String sql = """
        SELECT n.id                                   AS notificationId,
               n.text                                 AS text,
               n.created_date                         AS createdDate,
               n.is_read                              AS isRead,
               n.type                                 AS type,
               u.id                                   AS fromUserId,
               CONCAT(u.first_name, ' ', u.last_name) AS fullName,
               u.image                                AS imageUser,
               b.id                                   AS boardId,
               b.title                                AS titleBoard,
               b.back_ground                          AS backGround,
               c.id                                   AS columnId,
               c.title                                AS columnName,
               c2.id                                  AS cardId
        FROM notifications AS n
                 LEFT JOIN boards b ON b.id = n.board_id
                 LEFT JOIN columns c ON c.id = n.column_id
                 LEFT JOIN users u ON n.from_user_id = u.id
                 LEFT JOIN cards c2 ON c2.id = n.card_id
        WHERE n.id = ?
        GROUP BY n.id, n.text, n.created_date, n.is_read, n.type, u.id,
                 CONCAT(u.first_name, ' ', u.last_name), u.image, b.id, b.title,
                 b.back_ground, c.id, c.title, c2.id;
    """;

            List<NotificationResponse> notificationResponses = jdbcTemplate.query(
                    sql,
                    new Object[]{notificationId},
                    (rs, rowNum) ->  {
                        NotificationResponse response = new NotificationResponse();
                        response.setNotificationId(rs.getLong("notificationId"));
                        response.setText(rs.getString("text"));
                        response.setCreatedDate(rs.getString("createdDate"));
                        response.setIsRead(rs.getBoolean("isRead"));
                        response.setNotificationType(NotificationType.valueOf(rs.getString("type")));
                        response.setFromUserId(rs.getLong("fromUserId"));
                        response.setFullName(rs.getString("fullName"));
                        response.setImageUser(rs.getString("imageUser"));
                        response.setBoardId(rs.getLong("boardId"));
                        response.setTitleBoard(rs.getString("titleBoard"));
                        response.setBackGround(rs.getString("backGround"));
                        response.setColumnId(rs.getLong("columnId"));
                        response.setColumnName(rs.getString("columnName"));
                        response.setCardId(rs.getLong("cardId"));

                        return response;
                    });

            if (!notificationResponses.isEmpty()) {
                markNotificationAsRead(notificationId);
            }

            return notificationResponses.isEmpty() ? null : notificationResponses.get(0);
        }

        private void markNotificationAsRead(long notificationId) {
            String updateSql = "UPDATE notifications SET is_read = true WHERE id = ?";
            jdbcTemplate.update(updateSql, notificationId);
        }

}
