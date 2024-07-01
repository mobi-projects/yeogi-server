package com.example.yeogiserver.post.domain;

import java.util.Optional;

public interface MemoRepository {
    Optional<Memo> findById(Long memoId);
}
