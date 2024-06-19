package com.example.yeogiserver.comment.domain;

import java.util.Optional;

public interface LikeRepository {
    void save(Like like);
    void delete(Like like);
    Boolean existsByMemberEmailAndCommentId(String email, Long commentId);

    Long countById(Long postId);
}
