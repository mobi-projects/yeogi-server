package com.example.yeogiserver.comment.domain;

import java.util.Optional;

public interface LikeRepository {
    void save(Like like);
    void delete(Like like);
    Optional<Like> existsByMemberIdAndCommentId(Long memberId, Long commentId);

    Long countById(Long postId);
}
