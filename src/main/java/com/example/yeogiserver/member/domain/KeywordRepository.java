package com.example.yeogiserver.member.domain;

public interface KeywordRepository {

    boolean existsByMember(Long memberId);

    void deleteByMember(Long memberId);

    Keyword save(Keyword keyword);
}
