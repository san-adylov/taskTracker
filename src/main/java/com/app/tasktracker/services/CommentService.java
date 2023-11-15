package com.app.tasktracker.services;


import com.app.tasktracker.dto.request.CommentRequest;
import com.app.tasktracker.dto.response.CommentResponse;
import com.app.tasktracker.dto.response.SimpleResponse;

import java.util.List;

public interface CommentService {

    List<CommentResponse> getAllUserComments();
    List<CommentResponse> getAllComments();
    List<CommentResponse> getAllCommentsFromCard(Long cardId);

    SimpleResponse saveComment(CommentRequest commentRequest);

    CommentResponse getCommentById(Long commentId);

    SimpleResponse updateCommentById(Long commentId, CommentRequest commentRequest);

    SimpleResponse deleteCommentById(Long commentId);

}
