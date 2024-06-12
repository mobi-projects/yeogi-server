package com.example.yeogiserver.member.repository;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public void delete(String email) {
        memberJpaRepository.deleteByEmail(email);
    }

    @Override
    public List<Member> findAllByIds(List<Long> memberIds) {
        return memberJpaRepository.findAllById(memberIds);
    }
}
