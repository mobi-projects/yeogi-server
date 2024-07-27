package com.example.yeogiserver.comment.domain;

public interface CommentLikeRepository {
    void save(CommentLike commentLike);
    void delete(CommentLike commentLike);
    Boolean existsByMemberEmailAndCommentId(String email, Long commentId);

    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);

    Long countById(Long postId);
}
