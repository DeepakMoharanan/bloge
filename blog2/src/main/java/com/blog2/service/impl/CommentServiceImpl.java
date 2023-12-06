package com.blog2.service.impl;

import com.blog2.entity.Comment;
import com.blog2.entity.Post;
import com.blog2.exception.BlogAPIException;
import com.blog2.exception.ResourceNotFoundException;
import com.blog2.payload.CommentDto;
import com.blog2.repositiories.CommentRepository;
import com.blog2.repositiories.PostRepository;
import com.blog2.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }



    @Override
    public CommentDto saveCommentForPost(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));


        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to that post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        // getPostId() 2
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with postId: "+ postId));

            //comment.getPost().getPostId() 2
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with Id: "+ id));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belongs to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);

    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with postId: "+ postId));

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with Id: "+ id));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException("Comment does not belongs to post");
        }
        commentRepository.deleteById(id);
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment comment =new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        return comment;
    }
    CommentDto mapToDto(Comment comment){
        CommentDto dto =new CommentDto();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        dto.setEmail(comment.getEmail());
        dto.setName(comment.getName());
        return dto;
    }

}


