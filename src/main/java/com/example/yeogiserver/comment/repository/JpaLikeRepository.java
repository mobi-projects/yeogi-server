package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> existsByMemberEmailAndCommentId(String email, Long commentId);
    long countById(Long postId);

}
