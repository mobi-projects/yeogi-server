package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.MemberRepository;
import com.example.yeogiserver.security.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultMemberRepository implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id);
    }

    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

}
