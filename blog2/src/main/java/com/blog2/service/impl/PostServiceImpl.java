package com.blog2.service.impl;

import com.blog2.entity.Post;
import com.blog2.exception.ResourceNotFoundException;
import com.blog2.payload.PostDto;
import com.blog2.payload.PostResponse;
import com.blog2.repositiories.PostRepository;
import com.blog2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto){
        Post post=mapToEntity(postDto);

        Post savedPost = postRepository.save(post);

        PostDto dto=mapToDto(savedPost);

        return dto;
    }

    @Override
//    public List<PostDto> getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir)

    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
//                Condition1:Condition2;
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable= PageRequest.of(pageNo,pageSize, sort);
       Page<Post> content =postRepository.findAll(pageable);
       List<Post> posts= content.getContent();

//        List<PostDto> postDtos=posts.stream().map(
//                post -> mapToDto(post)).collect(Collectors.toList());
        List<PostDto> dtos=posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElements(content.getTotalElements());
        postResponse.setLast(content.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post= postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id : "+ id)
        );
        PostDto postDto = mapToDto(post);
        return postDto;
    }

    Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;

    }
    PostDto mapToDto(Post post){
        //Entity to Dto
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        return dto;

    }

}
