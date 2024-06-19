package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
