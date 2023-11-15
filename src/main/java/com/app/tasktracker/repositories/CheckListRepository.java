package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.tasktracker.models.CheckList;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList,Long> {

    @Query("select c from CheckList c where c.card.id = :cardId")
    List<CheckList> findAllCheckListByCardId(Long cardId);

    @Query("select case when count(ch) > 0 then true else false end from CheckList ch where ch.card.id = :cardId")
    boolean doesCheckListExistForCard(@Param("cardId") Long cardId);

}
