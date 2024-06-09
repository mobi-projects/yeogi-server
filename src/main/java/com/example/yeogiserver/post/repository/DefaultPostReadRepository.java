package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultPostReadRepository implements PostReadRepository {

    private final JpaPostRepository jpaPostRepository;

    private final JpaPostLikeRepository jpaPostLikeRepository;

    @Override
    public Optional<Post> findById(Long postId) {
        return jpaPostRepository.findById(postId);
    }
}
