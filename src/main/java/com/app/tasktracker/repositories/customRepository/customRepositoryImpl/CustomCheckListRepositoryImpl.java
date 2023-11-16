package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.dto.response.CheckListResponse;
import com.app.tasktracker.dto.response.ItemResponse;
import com.app.tasktracker.repositories.customRepository.CustomCheckListRepository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomCheckListRepositoryImpl implements CustomCheckListRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CheckListResponse> getAllCheckListByCardId(Long cardId) {

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
}