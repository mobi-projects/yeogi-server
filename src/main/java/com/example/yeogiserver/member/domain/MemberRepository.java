package com.example.yeogiserver.member.domain;


import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    void delete(String email);

    List<Member> findAllByIds(List<Long> memberIds);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
