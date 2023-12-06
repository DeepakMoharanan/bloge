package com.blog2.controller;

import com.blog2.payload.PostDto;
import com.blog2.payload.PostResponse;
import com.blog2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts?pageNo=1&pageSize=4
//    http://localhost:8080/api/posts?pageNo=0&pageSize=4&sortBy=id
//    http://localhost:8080/api/posts?pageNo=0&pageSize=4&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue ="0", required =false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "4",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PostResponse postResponse= postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postResponse;

    }
    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id")Long id){
        PostDto dto= postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}