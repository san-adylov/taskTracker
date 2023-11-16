package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.dto.response.*;
import com.app.tasktracker.enums.ReminderType;
import com.app.tasktracker.exceptions.*;
import com.app.tasktracker.exceptions.IllegalArgumentException;
import com.app.tasktracker.models.*;
import com.app.tasktracker.repositories.*;
import com.app.tasktracker.repositories.jdbcTemplateService.CustomCardJdbcTemplateService;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomCardJdbcTemplateServiceImpl implements CustomCardJdbcTemplateService {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final CustomAttachmentRepositoryImpl attachmentRepository;

    public User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User not found!"));
    }

    @Override
    public CardInnerPageResponse getAllCardInnerPage(Long cardId) {
        String query = """
                select c.id as cardId,
                       c.title as title,
                       c.description as description,
                       c.is_archive as isArchive
                       from cards as c where c.id = ?""";

        List<CardInnerPageResponse> cardInnerPageResponses = jdbcTemplate.query(query, (rs, rowNum) -> {
            CardInnerPageResponse response = new CardInnerPageResponse();
            response.setCardId(rs.getLong("cardId"));
            response.setTitle(rs.getString("title"));
            response.setDescription(rs.getString("title"));
            response.setIsArchive(rs.getBoolean("isArchive"));
            long estimationId = rs.getLong("cardId");
            response.setEstimationResponse(getEstimationByCardId(estimationId));

            List<LabelResponse> labelResponses = getLabelResponsesByCardId(rs.getLong("cardId"));
            response.setLabelResponses(labelResponses != null && !labelResponses.isEmpty() ? labelResponses : Collections.emptyList());

            List<CheckListResponse> checklistResponses = getCheckListResponsesByCardId(rs.getLong("cardId"));
            response.setChecklistResponses(checklistResponses != null && !checklistResponses.isEmpty() ? checklistResponses : Collections.emptyList());

            List<UserResponse> userResponses = getMembersResponsesByCardId(rs.getLong("cardId"));
            response.setUserResponses(userResponses != null && !userResponses.isEmpty() ? userResponses : Collections.emptyList());

            List<CommentResponse> commentResponses = getCommentsResponsesByCardId(rs.getLong("cardId"));
            response.setCommentResponses(commentResponses != null && !commentResponses.isEmpty() ? commentResponses : Collections.emptyList());

            return response;
        }, cardId);

        if (cardInnerPageResponses.isEmpty()) {
            new CardInnerPageResponse();
        }
        return cardInnerPageResponses.get(0);
    }

    private EstimationResponse getEstimationByCardId(Long cardId) {
        String sql = """
                    SELECT e.id AS estimationId,
                           e.start_date AS startDate,
                           e.start_time as startTime,
                           e.due_date AS dueDate,
                           e.finish_time AS time,
                           e.reminder_type AS reminderType
                           FROM cards AS ca LEFT JOIN estimations AS e ON ca.id = e.card_id
                           WHERE ca.id = ?
                """;

        List<EstimationResponse> estimations = jdbcTemplate.query(sql, (rs, rowNum) -> {
            EstimationResponse estimationResponse = new EstimationResponse();

            Long estimationId = rs.getLong("estimationId");
            if (!rs.wasNull()) {
                estimationResponse.setEstimationId(estimationId);
            }
            Timestamp startDateTimestamp = rs.getTimestamp("startDate");
            if (!rs.wasNull()) {
                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime startDateZoned = startDateTimestamp.toInstant().atZone(zoneId);
                String formattedStartDate = startDateZoned.format(DateTimeFormatter.ofPattern("d MMM yyyy 'at' h:mm a"));
                estimationResponse.setStartDate(formattedStartDate);
            }
            Timestamp startTimeTimestamp = rs.getTimestamp("startTime");
            if (!rs.wasNull()) {
                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime startTimeZoned = startTimeTimestamp.toInstant().atZone(zoneId);
                String formattedStartTime = startTimeZoned.format(DateTimeFormatter.ofPattern("d MMM yyyy 'at' h:mm a"));
                estimationResponse.setStartTime(formattedStartTime);
            }

            Timestamp dueDateTimestamp = rs.getTimestamp("dueDate");
            if (!rs.wasNull()) {
                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime dueDateZoned = dueDateTimestamp.toInstant().atZone(zoneId);
                String formattedDueDate = dueDateZoned.format(DateTimeFormatter.ofPattern("d MMM yyyy 'at' h:mm a"));
                estimationResponse.setDuetDate(formattedDueDate);
            }

            OffsetDateTime timeOffset = rs.getObject("time", OffsetDateTime.class);
            if (!rs.wasNull()) {
                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime timeZoned = timeOffset.toInstant().atZone(zoneId);
                String formattedTime = timeZoned.format(DateTimeFormatter.ofPattern("h:mm a"));
                estimationResponse.setFinishTime(formattedTime);
            }

            String reminderTypeStr = rs.getString("reminderType");
            if (!rs.wasNull()) {
                try {
                    estimationResponse.setReminderType(ReminderType.valueOf(reminderTypeStr));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid reminderType");
                }
            }

            return estimationResponse;
        }, cardId);

        if (estimations.isEmpty()) {
            return new EstimationResponse();
        }
        return estimations.get(0);
    }

    private List<LabelResponse> getLabelResponsesByCardId(Long cardId) {
        String sql = """
                SELECT l.id AS labelId,
                       l.label_name AS label_name,
                       l.color AS color
                       FROM labels AS l
                       JOIN labels_cards lc ON l.id = lc.labels_id
                       WHERE lc.cards_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LabelResponse labelResponse = new LabelResponse();
            labelResponse.setLabelId(rs.getLong("labelId"));
            labelResponse.setDescription(rs.getString("label_name"));
            labelResponse.setColor(rs.getString("color"));

            return labelResponse;
        }, cardId);
    }

    private List<CheckListResponse> getCheckListResponsesByCardId(Long cardId) {
        String sql = """
                SELECT cl.id AS checkListId,
                             cl.title AS title,
                             COUNT(i.id) AS numberItems,
                             SUM(CASE WHEN i.is_done = true THEN 1 ELSE 0 END) AS numberCompletedItems,
                             CASE WHEN COUNT(i.id) > 0
                                 THEN (SUM(CASE WHEN i.is_done = true THEN 1 ELSE 0 END) * 100.0) / COUNT(i.id)
                                 ELSE 0
                             END AS percent
                      FROM check_lists AS cl
                      JOIN cards c ON c.id = cl.card_id
                      LEFT JOIN items i ON cl.id = i.check_list_id
                      WHERE c.id = ?
                      GROUP BY cl.id, cl.title, cl.percent
                  """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CheckListResponse checkListResponse = new CheckListResponse();
            checkListResponse.setCheckListId(rs.getLong("checkListId"));
            checkListResponse.setTitle(rs.getString("title"));
            checkListResponse.setPercent(rs.getInt("percent"));

            int numberItems = rs.getInt("numberItems");
            int numberCompletedItems = rs.getInt("numberCompletedItems");
            String counter = numberCompletedItems + "/" + numberItems;

            checkListResponse.setCounter(counter);
            checkListResponse.setItemResponseList(getItemsByCheckListId(rs.getLong("checkListId")));

            return checkListResponse;
        }, cardId);
    }

    private List<ItemResponse> getItemsByCheckListId(Long checkListId) {
        String sql = """
                              SELECT i.id AS itemId,
                                     i.title AS title,
                                     i.is_done AS isDone
                              FROM items AS i
                              WHERE i.check_list_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setItemId(rs.getLong("itemId"));
            itemResponse.setTitle(rs.getString("title"));
            itemResponse.setIsDone(rs.getBoolean("isDone"));
            return itemResponse;
        }, checkListId);
    }

    private List<UserResponse> getMembersResponsesByCardId(Long cardId) {
        String sql = """
                SELECT u.id AS memberId,
                       u.first_name AS firstName,
                       u.last_name AS lastName,
                       u.email AS email,
                       u.image AS image
                       FROM users AS u 
                       JOIN cards_members cu ON u.id = cu.members_id 
                       WHERE cu.cards_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(rs.getLong("memberId"));
            userResponse.setFirstName(rs.getString("firstName"));
            userResponse.setLastName(rs.getString("lastName"));
            userResponse.setEmail(rs.getString("email"));
            userResponse.setAvatar(rs.getString("image"));

            return userResponse;
        }, cardId);
    }

    private List<CommentResponse> getCommentsResponsesByCardId(Long cardId) {
        User user = getAuthentication();
        String sql = """
                SELECT co.id AS commentId,
                       co.comment AS comment,
                       co.created_date AS created_date,                       
                       u.id AS user_id,
                       CONCAT(u.first_name, ' ', u.last_name) AS fullName,
                       CASE WHEN u.id = ? THEN TRUE ELSE FALSE END AS isMine,
                       u.image AS image
                FROM comments AS co
                JOIN cards c ON c.id = co.card_id
                JOIN users u ON co.member_id = u.id
                WHERE c.id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(rs.getLong("commentId"));
            commentResponse.setComment(rs.getString("comment"));

            Timestamp timestamp = rs.getTimestamp("created_date");
            if (timestamp != null) {
                ZonedDateTime zonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy / h:mma");
                String formattedDateTime = zonedDateTime.format(formatter);
                commentResponse.setCreatedDate(formattedDateTime);
                commentResponse.setCreatorId(rs.getLong("user_id"));
                commentResponse.setCreatorName(rs.getString("fullName"));
                commentResponse.setCreatorAvatar(rs.getString("image"));
                commentResponse.setIsMyComment(rs.getBoolean("isMine"));
            }
            return commentResponse;
        }, user.getId(),cardId);
    }

    @Override
    public List<CardResponse> getAllCardsByColumnId(Long columnId) {
        String query = """
                SELECT c.id AS cardId,
                       c.title AS title,
                       c.description AS description,
                       e.start_date AS startDate,
                       e.due_date AS dueDate,
                       (SELECT COUNT(*) FROM cards_members AS cu WHERE cu.cards_id = c.id) AS numberUsers,
                       (SELECT COUNT(*) FROM items AS i
                       JOIN check_lists AS cl ON i.check_list_id = cl.id
                       WHERE i.is_done = true AND cl.card_id = c.id) AS numberCompletedItems,
                       (SELECT COUNT(*) FROM check_lists AS cl WHERE cl.card_id = c.id) AS numberItems
                FROM cards AS c
                LEFT JOIN estimations AS e ON c.id = e.card_id
                WHERE c.column_id = ? and c.is_archive = false
                """;

        List<CardResponse> cardResponses = jdbcTemplate.query(query, new Object[]{columnId}, (rs, rowNum) -> {
            CardResponse cardResponse = new CardResponse();
            cardResponse.setCardId(rs.getLong("cardId"));
            cardResponse.setTitle(rs.getString("title"));
            cardResponse.setDescription(rs.getString("description"));

            ZoneId zoneId = ZoneId.systemDefault();

            ZonedDateTime startDate = rs.getTimestamp("startDate") != null ? rs.getTimestamp("startDate").toLocalDateTime().atZone(zoneId) : null;
            ZonedDateTime dueDate = rs.getTimestamp("dueDate") != null ? rs.getTimestamp("dueDate").toLocalDateTime().atZone(zoneId) : null;

            if (startDate != null && dueDate != null) {
                Duration duration = Duration.between(startDate, dueDate);
                long days = duration.toDays();
                cardResponse.setDuration(days + " days");
            } else {
                cardResponse.setDuration("");
            }

            int numberUsers = rs.getInt("numberUsers");
            cardResponse.setNumberOfUsers(numberUsers);

            int numberItems = rs.getInt("numberItems");
            cardResponse.setNumberOfItems(numberItems);

            int numberCompletedItems = rs.getInt("numberCompletedItems");
            cardResponse.setNumberOfCompletedItems(numberCompletedItems);

            List<LabelResponse> labelResponses = getLabelResponsesForCard(rs.getLong("cardId"));
            cardResponse.setLabelResponses(labelResponses);

            List<CommentResponse> commentResponses = getCommentResponsesForCard(rs.getLong("cardId"));
            cardResponse.setCommentResponses(commentResponses);

            List<CheckListResponse> checkListResponses = getCheckListResponsesByCardId(rs.getLong("cardId"));
            cardResponse.setCheckListResponses(checkListResponses);

            List<AttachmentResponse> attachmentResponses = attachmentRepository.getAttachmentsByCardId(rs.getLong("cardId"));
            cardResponse.setAttachmentResponses(attachmentResponses);

            return cardResponse;
        });
        if (cardResponses.isEmpty()) {
            return new ArrayList<>();
        }
        return cardResponses;
    }


    private List<LabelResponse> getLabelResponsesForCard(Long cardId) {
        String sql = """
                SELECT l.id AS labelId,
                       l.label_name AS name,
                       l.color AS color 
                FROM labels AS l
                JOIN labels_cards lc ON l.id = lc.labels_id
                WHERE lc.cards_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LabelResponse labelResponse = new LabelResponse();
            labelResponse.setLabelId(rs.getLong("labelId"));
            labelResponse.setDescription(rs.getString("name"));
            labelResponse.setColor(rs.getString("color"));

            return labelResponse;
        }, cardId);
    }

    private List<CommentResponse> getCommentResponsesForCard(Long cardId) {
        User user = getAuthentication();
        String query = """
                SELECT co.id AS commentId,
                       co.comment AS comment,
                       co.created_date AS createdDate,
                       u.id AS userId,
                       CONCAT(u.first_name, ' ', u.last_name) AS fullName,
                       CASE WHEN u.id = ? THEN TRUE ELSE FALSE END AS isMine,
                       u.image AS image
                FROM comments AS co
                JOIN cards c ON c.id = co.card_id
                JOIN users u ON co.member_id = u.id
                WHERE c.id = ?
                """;

        return jdbcTemplate.query(query, (rs, rowNum) -> {
                    CommentResponse commentResponse = new CommentResponse();
                    commentResponse.setCommentId(rs.getLong("commentId"));
                    commentResponse.setComment(rs.getString("comment"));

                    Timestamp timestamp = rs.getTimestamp("createdDate");
                    if (timestamp != null) {
                        ZonedDateTime zonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy / h:mma");
                        String formattedDateTime = zonedDateTime.format(formatter);
                        commentResponse.setCreatedDate(formattedDateTime);
                        commentResponse.setCreatorId(rs.getLong("userId"));
                        commentResponse.setCreatorName(rs.getString("fullName"));
                        commentResponse.setCreatorAvatar(rs.getString("image"));
                        commentResponse.setIsMyComment(rs.getBoolean("isMine"));
                    }

                    return commentResponse;
                }
                , user.getId(),cardId);
    }
}