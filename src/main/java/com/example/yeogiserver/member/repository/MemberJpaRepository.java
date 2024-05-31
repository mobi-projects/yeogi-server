package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
