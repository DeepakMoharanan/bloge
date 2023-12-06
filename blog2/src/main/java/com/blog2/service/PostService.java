package com.blog2.service;

import com.blog2.payload.PostDto;
import com.blog2.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);
}
