package com.app.tasktracker.repositories.customRepository.customRepositoryImpl;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.dto.response.LabelResponse;
import com.app.tasktracker.exceptions.NotFoundException;
import com.app.tasktracker.repositories.customRepository.CustomLabelRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Getter
@Slf4j
public class CustomLabelServiceImpl implements CustomLabelRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<LabelResponse> getAllLabels() {
        String query = """
                SELECT  l.id AS  id,
                l.label_name AS  labelName,
                l.color   AS  labelColor
                FROM labels AS l
                """;
        return jdbcTemplate.query(query, ((rs, rowNum) -> {
            LabelResponse labelResponse = new LabelResponse();
            labelResponse.setLabelId(rs.getLong("id"));
            labelResponse.setDescription(rs.getString("labelName"));
            labelResponse.setColor(rs.getString("labelColor"));
            return labelResponse;
        }));
    }

    @Override
    public List<LabelResponse> getAllLabelsByCardId(Long cardId) {
        String query = """
                SELECT   l.id AS id,
                l.label_name AS labelName,
                l.color AS labelColor
                FROM labels AS
                l JOIN labels_cards lc ON l.id = lc.labels_id 
                WHERE lc.cards_id=?
                """;
        List<LabelResponse> labelResponses =
                jdbcTemplate.query(query, new Object[]{cardId}, (rs, rowNum) -> {
                    LabelResponse labelResponse = new LabelResponse();
                    labelResponse.setLabelId(rs.getLong("id"));
                    labelResponse.setDescription(rs.getString("labelName"));
                    labelResponse.setColor(rs.getString("labelColor"));
                    return labelResponse;
                });
        if (labelResponses.isEmpty()) {
            new ArrayList<>();
        }
        return labelResponses;
    }

    @Override
    public LabelResponse getLabelById(Long labelId) {
        String query = """
                 SELECT   l.id AS  id,
                 l.label_name AS  labelName,
                 l.color AS  labelColor 
                 FROM labels AS l where l.id=?
                """;
        List<LabelResponse> labelResponses = jdbcTemplate.query(query, new Object[]{labelId}, ((rs, rowNum) -> {
            LabelResponse labelResponse1 = new LabelResponse();
            labelResponse1.setLabelId(rs.getLong("id"));
            labelResponse1.setDescription(rs.getString("labelName"));
            labelResponse1.setColor(rs.getString("labelColor"));
            return labelResponse1;
        }));
        if (labelResponses.isEmpty()) {
            log.error(String.format("Label with id :%s doesn't exist !", labelId));
            throw new NotFoundException(String.format("Label with id :%s doesn't exist !", labelId));
        }
        return labelResponses.get(0);
    }

}
