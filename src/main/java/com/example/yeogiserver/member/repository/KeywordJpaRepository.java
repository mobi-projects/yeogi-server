package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.member.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordJpaRepository extends JpaRepository<Keyword, Long> {

    boolean existsByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);

    Keyword save(Keyword keyword);
}
