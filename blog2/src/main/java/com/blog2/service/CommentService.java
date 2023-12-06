package com.blog2.service;

import com.blog2.exception.BlogAPIException;
import com.blog2.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveCommentForPost(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComment(long postId, long id);
}
