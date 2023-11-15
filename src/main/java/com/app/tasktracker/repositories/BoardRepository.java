package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app.tasktracker.models.Board;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board  b where b.workSpace.id=:workSpaceId")
    List<Board> getAllByBoards(Long workSpaceId);
}