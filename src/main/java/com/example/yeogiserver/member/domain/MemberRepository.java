package com.example.yeogiserver.member.domain;

public interface MemberRepository {

    Member save(Member member);

    Token saveToken(Token token);

    boolean isTokenExist(String token);

}
