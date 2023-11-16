package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.app.tasktracker.dto.response.AllIssuesResponse;
import com.app.tasktracker.repositories.customRepository.CustomAllIssuesRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Repository
@Transactional
@RequiredArgsConstructor
public class CustomAllIssuesRepositoryImpl implements CustomAllIssuesRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<AllIssuesResponse> filterIssues(Long workSpaceId, LocalDate from, LocalDate to, List<Long> labelIds, List<Long> assigneeMemberIds) {
        String sql = """
                    select distinct on (cr.id) cr.id,
                                        cr.created_date,
                                        DATE_PART('day', NOW() - cr.created_date),
                                        cr.creator_id,
                                        concat(u.first_name, '  ', u.last_name),
                                        col.title,
                                        (
                                            select COUNT(*)
                                            from check_lists chk
                                            where chk.card_id = cr.id
                                        ),
                                        cr.description
                    from cards cr
                    inner join columns col on cr.column_id = col.id
                    inner join boards b on col.board_id = b.id
                    inner join work_spaces w on b.work_space_id = w.id
                    left join cards_members cm on cr.id = cm.cards_id
                    left join labels_cards lc on cr.id = lc.cards_id
                    left join users u on cr.creator_id = u.id
                    where w.id = :workSpaceId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("workSpaceId", workSpaceId);
        params.addValue("from", from);
        params.addValue("to", to);
        params.addValue("labelIds", labelIds);
        params.addValue("assigneeMemberIds", assigneeMemberIds);

        if (from != null) {
            sql += " AND cr.created_date >= :from";
            params.addValue("from", from);
        }
        if (to != null) {
            sql += " AND cr.created_date <= :to";
            params.addValue("to", to);
        }

        if (labelIds != null && !labelIds.isEmpty()) {
            sql += " AND lc.labels_id IN (:labelIds)";
            params.addValue("labelIds", labelIds);
        }
        if (assigneeMemberIds != null && !assigneeMemberIds.isEmpty()) {
            sql += " AND cm.members_id IN (:assigneeMemberIds)";
            params.addValue("assigneeMemberIds", assigneeMemberIds);
        }

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> new AllIssuesResponse(
                rs.getLong(1),
                rs.getDate(2).toLocalDate(),
                rs.getString(3),
                rs.getLong(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8)
        ));
    }
}