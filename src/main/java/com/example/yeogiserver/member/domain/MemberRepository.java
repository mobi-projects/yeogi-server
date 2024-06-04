package com.example.yeogiserver.member.domain;

import com.example.yeogiserver.commom.domain.Token;

public interface MemberRepository {

    Member save(Member member);

    Token saveToken(Token token);

    boolean isTokenExist(String token);

}
