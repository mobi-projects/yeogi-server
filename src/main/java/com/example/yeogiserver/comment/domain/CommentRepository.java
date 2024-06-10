package com.example.yeogiserver.comment.domain;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment saveComment(Comment comment);

    List<Comment> findByPostId(Long id);
    Optional<Comment> findById(Long id);

    void deleteByPostId(Long postId);
    void deleteByCommentId(Long id);

    Like saveLike(Like Like);


}
