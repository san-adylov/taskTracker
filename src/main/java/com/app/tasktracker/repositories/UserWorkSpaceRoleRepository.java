package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.tasktracker.models.UserWorkSpaceRole;

import java.util.List;

public interface UserWorkSpaceRoleRepository extends JpaRepository<UserWorkSpaceRole,Long> {

    @Query("select u from UserWorkSpaceRole u where u.member.id = :userId and u.workSpace.id = :workSpaceId")
    UserWorkSpaceRole findByUserIdAndWorkSpacesId(Long userId,Long workSpaceId);

    @Query("select u from UserWorkSpaceRole u JOIN u.workSpace w join w.boards b where b.id = :boardId and u.member.id = :userId")
    List<UserWorkSpaceRole> findByUserId(@Param("boardId") Long boardId, @Param("userId") Long userId);


    @Query("select u from UserWorkSpaceRole u where u.member.id = :userId and u.workSpace.id = :workSpaceId")
    List<UserWorkSpaceRole>findByUserToWorkSpace(@Param("userId") Long userId,@Param("workSpaceId") Long workSpaceId);

}