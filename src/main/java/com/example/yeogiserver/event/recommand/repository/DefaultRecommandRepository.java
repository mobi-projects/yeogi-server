package com.example.yeogiserver.event.recommand.repository;

import com.example.yeogiserver.event.recommand.domain.Recommand;
import com.example.yeogiserver.event.recommand.domain.RecommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultRecommandRepository implements RecommandRepository {
    private final JpaRecommandRepository jpaRecommandRepository;
    @Override
    public Optional<Recommand> findByMemberId(Long memberId) {
        return jpaRecommandRepository.findByMemberId(memberId);
    }

    @Override
    public void save(Recommand recommand) {
        jpaRecommandRepository.save(recommand);
    }
}
