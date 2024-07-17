package com.example.yeogiserver.event.recommand.domain;

import java.util.Optional;

public interface RecommandRepository {
    Optional<Recommand> findByMemberId(Long memberId);

     void save(Recommand recommand);
}
