package com.example.yeogiserver.member.domain;


import java.util.List;

public interface MemberRepository {

    Member save(Member member);

    List<Member> findAllByIds(List<Long> memberIds);
}
