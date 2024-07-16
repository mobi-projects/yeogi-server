package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.member.domain.Keyword;
import com.example.yeogiserver.member.domain.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DefaultKeywordRepository implements KeywordRepository {

    private final KeywordJpaRepository keywordJpaRepository;

    @Override
    public boolean existsByMember(Long memberId) {
        return keywordJpaRepository.existsByMemberId(memberId);
    }

    @Override
    public void deleteByMember(Long memberId) {
        keywordJpaRepository.deleteAllByMemberId(memberId);
    }

    @Override
    public Keyword save(Keyword keyword) {
        return keywordJpaRepository.save(keyword);
    }
}
