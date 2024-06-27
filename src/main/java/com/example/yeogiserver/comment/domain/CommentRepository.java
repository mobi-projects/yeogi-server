package com.example.yeogiserver.comment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment saveComment(Comment comment);

    List<Comment> findByPostId(Long id, Pageable pageable);
    Optional<Comment> findById(Long id);

    void deleteByPostId(Long postId);
    void deleteByCommentId(Long id);


    Long countByPostId(Long postId);
}
