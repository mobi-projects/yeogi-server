package com.example.yeogiserver.post.domain;

import java.util.Optional;

public interface PostRepository {
    Post savePost(Post post);

    Optional<Post> findById(Long id);

    void deleteById(Long id);
}
