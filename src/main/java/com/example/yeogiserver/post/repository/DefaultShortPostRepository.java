package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.ShortPost;
import com.example.yeogiserver.post.domain.ShortPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultShortPostRepository implements ShortPostRepository {

    private final JpaMemoRepository jpaMemoRepository;

    @Override
    public Optional<ShortPost> findById(Long memoId) {
        return jpaMemoRepository.findById(memoId);
    }
}
