package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.CommentResponse;

import java.util.List;

public interface CustomCommentRepository {
    List<CommentResponse> getAllUserComments(Long userId);
    List<CommentResponse> getAllCommentByCardId(Long cardId);
    List<CommentResponse> getAllComments();
    CommentResponse getCommentById(Long commentId);

}
