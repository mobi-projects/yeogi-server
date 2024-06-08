package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);

    Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId);
}
