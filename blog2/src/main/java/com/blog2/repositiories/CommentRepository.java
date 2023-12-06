package com.blog2.repositiories;

import com.blog2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);
//    List<Comment> findByPostId(long postId);
}
