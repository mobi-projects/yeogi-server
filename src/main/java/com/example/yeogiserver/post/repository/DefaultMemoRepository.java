package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Memo;
import com.example.yeogiserver.post.domain.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultMemoRepository implements MemoRepository {

    private final JpaMemoRepository jpaMemoRepository;

    @Override
    public Optional<Memo> findById(Long memoId) {
        return jpaMemoRepository.findById(memoId);
    }
}
