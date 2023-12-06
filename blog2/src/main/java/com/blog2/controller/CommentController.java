package com.blog2.controller;

import com.blog2.payload.CommentDto;
import com.blog2.payload.PostResponse;
import com.blog2.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    http://localhost:8080/api/posts/1/comments

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId") long postId,
            @RequestBody CommentDto commentDto

    ){
        CommentDto dto= commentService.saveCommentForPost(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //    http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId){
        List<CommentDto> commentDto= commentService.getCommentByPostId(postId);
        return commentDto;
    }
//    http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "id") long commentId){
        CommentDto Dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(Dto, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id,
            @RequestBody CommentDto commentDto
            )
    {
        CommentDto dto= commentService.updateComment(postId,id,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }


    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment( @PathVariable("postId") long postId,
                                                 @PathVariable("id") long id
    ){
    commentService.deleteComment(postId,id);
    return new ResponseEntity<>("Comment is Deleted ", HttpStatus.OK);

    }

}
