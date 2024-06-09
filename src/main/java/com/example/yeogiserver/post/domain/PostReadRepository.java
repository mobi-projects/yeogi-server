package com.example.yeogiserver.post.domain;

import java.util.Optional;

public interface PostReadRepository {
    Optional<Post> findById(Long postId);
}
