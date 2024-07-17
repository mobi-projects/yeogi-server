package com.example.yeogiserver.event.recommand.repository;

import com.example.yeogiserver.event.recommand.domain.Recommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRecommandRepository extends JpaRepository<Recommand, Long> {
    Optional<Recommand> findByMemberId(Long memberId);
}
