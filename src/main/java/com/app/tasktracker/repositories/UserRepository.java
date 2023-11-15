package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.tasktracker.dto.response.UserAllIssuesResponse;
import com.app.tasktracker.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

    @Query("select case when count(u)>0 then true else false end from User u where u.email ilike :email")
    boolean existsByEmail(String email);

    @Query("select u from User u where u.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u.id From User u JOIN u.roles r JOIN r.workSpace ws WHERE ws.id = ?1")
    List<Long> getAllUsersByWorkSpaseId(Long workSpaseId);

    @Query("SELECT u FROM User u WHERE u.email =:email")
    User findUserByEmailParticipants(@Param("email") String email);

    @Query("""
            select new com.app.tasktracker.dto.response.UserAllIssuesResponse(
            u.id,
            concat(u.firstName, ' ', u.lastName),
            u.image,
            u.email)
            from User u
            join u.cards c
            where c.id = :cardId
            """)
    List<UserAllIssuesResponse> findAllParticipantByCardId(Long cardId);
}