package com.example.yeogiserver.post.domain;

import java.util.Optional;

public interface ShortPostRepository {
    Optional<ShortPost> findById(Long memoId);
}
