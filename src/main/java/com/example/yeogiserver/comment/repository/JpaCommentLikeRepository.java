package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentLikeRepository extends JpaRepository<CommentLike,Long> {
    Boolean existsByMemberEmailAndCommentId(String email, Long commentId);
    long countById(Long postId);
    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);
}
