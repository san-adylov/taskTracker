package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.app.tasktracker.dto.response.LabelResponse;
import com.app.tasktracker.models.Label;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label,Long> {

    @Query("select new com.app.tasktracker.dto.response.LabelResponse(l.id,l.labelName,l.color) from Label l")
    List<LabelResponse> getAllLabelResponse();

    @Query("""
           select new com.app.tasktracker.dto.response.LabelResponse(
           l.id,
           l.labelName,
           l.color)
           from Label l
           join l.cards c
           where c.id = :cardId
           """)
    List<LabelResponse> findAllByCardId(Long cardId);
}