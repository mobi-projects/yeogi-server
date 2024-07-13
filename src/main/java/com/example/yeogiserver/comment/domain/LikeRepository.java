package com.example.yeogiserver.comment.domain;

public interface LikeRepository {
    void save(Like like);
    void delete(Like like);
    Boolean existsByMemberEmailAndCommentId(String email, Long commentId);

    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);

    Long countById(Long postId);
}
