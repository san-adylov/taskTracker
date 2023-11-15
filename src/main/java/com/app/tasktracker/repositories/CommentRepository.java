package com.app.tasktracker.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.app.tasktracker.models.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Optional<Comment> getCommentById(Long commentId);
}

